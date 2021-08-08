package com.example.application.views.mahasiswa;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.application.entity.Mahasiswa;
import com.example.application.entity.TempatLahir;
import com.example.application.repo.MahasiswaRepo;
import com.example.application.repo.TempatLahirRepo;
import com.example.application.service.MahasiswaService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.crudui.crud.LazyCrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.data.spring.OffsetBasedPageRequest;

@Route(value = "mahasiswa", layout = MainView.class)
@Secured("ROLE_USER")
@PageTitle(value = "Mahasiswa")
public class MahasiswaView extends VerticalLayout {

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        if (attachEvent.isInitialAttach()) {
            generateComponent();
            setSizeFull();
        }
    }

    private MahasiswaService mahasiswaService;

    @Autowired
    private MahasiswaRepo mahasiswaRepo;

    @Autowired
    private TempatLahirRepo tempatLahirRepo;

    @Autowired
    public MahasiswaView(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;

    }

    private void generateComponent() {
        GridCrud<Mahasiswa> gridCrud = new GridCrud<>(Mahasiswa.class);
        TextField filter = new TextField();

        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);

        gridCrud.getCrudLayout().addFilterComponent(filter);

        gridCrud.getCrudLayout().addToolbarComponent(new Button("Export"));

        gridCrud.getGrid().setPageSize(50);

        gridCrud.getGrid().setColumns("nama");

        gridCrud.getGrid().addColumn("tempatLahir.nama").setHeader("Tempat Lahir");

        gridCrud.getGrid()
                .addColumn(new LocalDateRenderer<>(Mahasiswa::getTglLahir,
                        DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("in", "ID"))))
                .setHeader("Tanggal Lahir");

        gridCrud.getCrudFormFactory().setUseBeanValidation(true);

        gridCrud.getCrudFormFactory().setVisibleProperties("nama", "tglLahir", "tempatLahir");

        gridCrud.getCrudFormFactory().setFieldProvider("tempatLahir", new ComboBoxProvider<>("Tempat Lahir",
                tempatLahirRepo.findAll(), new TextRenderer<>(TempatLahir::getNama), TempatLahir::getNama));

        filter.addValueChangeListener(e -> gridCrud.refreshGrid());

        gridCrud.setCrudListener(new LazyCrudListener<Mahasiswa>() {

            @Override
            public Mahasiswa add(Mahasiswa domainObjectToAdd) {
                return mahasiswaService.addMahasiswa(domainObjectToAdd);
            }

            @Override
            public Mahasiswa update(Mahasiswa domainObjectToUpdate) {
                return mahasiswaService.updateMahasiswa(domainObjectToUpdate);
            }

            @Override
            public void delete(Mahasiswa domainObjectToDelete) {
                mahasiswaService.deleteMahasiswa(domainObjectToDelete);
            }

            @Override
            public DataProvider<Mahasiswa, ?> getDataProvider() {
                return DataProvider.fromCallbacks(
                        query -> mahasiswaRepo.findByNamaContainsIgnoreCaseAndDeletedOrderByAuditDateDesc(
                                filter.getValue(), false, new OffsetBasedPageRequest(query)).stream(),
                        query -> (int) mahasiswaRepo.countByNamaContainsIgnoreCaseAndDeleted(filter.getValue(), false));
            }

        });
        add(gridCrud);
    }
}
