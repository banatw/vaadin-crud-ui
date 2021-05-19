package com.example.application.views.mahasiswa;

import com.example.application.entity.Mahasiswa;
import com.example.application.entity.TempatLahir;
import com.example.application.repo.MahasiswaRepo;
import com.example.application.repo.TempatLahirRepo;
import com.example.application.service.MahasiswaService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.LazyCrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.data.spring.OffsetBasedPageRequest;

@Route(value = "mahasiswa", layout = MainView.class)
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

        gridCrud.getGrid().setPageSize(50);

        gridCrud.getGrid().setColumns("nama", "tglLahir");

        gridCrud.getGrid().addColumn("tempatLahir.nama").setHeader("Tempat Lahir");

        gridCrud.getCrudFormFactory().setUseBeanValidation(true);

        gridCrud.getCrudFormFactory().setVisibleProperties("nama", "tglLahir", "tempatLahir");

        gridCrud.getCrudFormFactory().setFieldProvider("tempatLahir", new ComboBoxProvider<>("Tempat Lahir",
                tempatLahirRepo.findAll(), new TextRenderer<>(TempatLahir::getNama), TempatLahir::getNama));

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
                        query -> mahasiswaRepo.findAll(new OffsetBasedPageRequest(query)).stream(),
                        query -> (int) mahasiswaRepo.count());
            }

        });
        add(gridCrud);
    }
}