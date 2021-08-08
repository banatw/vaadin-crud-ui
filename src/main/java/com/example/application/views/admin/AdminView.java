package com.example.application.views.admin;

import com.example.application.entity.AppUser;
import com.example.application.repo.AppUserRepo;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.crudui.crud.LazyCrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.data.spring.OffsetBasedPageRequest;

@Route(value = "admin")
@Secured("ROLE_ADMIN")
public class AdminView extends VerticalLayout {
    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        if (attachEvent.isInitialAttach()) {
            generateComponent();
            setSizeFull();
        }
    }

    private void generateComponent() {
        GridCrud<AppUser> gridCrud = new GridCrud<>(AppUser.class);
        TextField filter = new TextField();

        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);

        gridCrud.getCrudLayout().addFilterComponent(filter);

        gridCrud.getCrudLayout().addToolbarComponent(new Anchor("/logout", "Logout"));

        gridCrud.getGrid().setPageSize(50);

        gridCrud.getGrid().setColumns("username", "password");

        gridCrud.getCrudFormFactory().setUseBeanValidation(true);

        gridCrud.getCrudFormFactory().setVisibleProperties("username", "password");

        filter.addValueChangeListener(e -> gridCrud.refreshGrid());

        gridCrud.setCrudListener(new LazyCrudListener<AppUser>() {

            @Override
            public AppUser add(AppUser domainObjectToAdd) {
                // TODO Auto-generated method stub
                return appUserRepo.save(domainObjectToAdd);
            }

            @Override
            public AppUser update(AppUser domainObjectToUpdate) {
                // TODO Auto-generated method stub
                return appUserRepo.save(domainObjectToUpdate);
            }

            @Override
            public void delete(AppUser domainObjectToDelete) {
                appUserRepo.delete(domainObjectToDelete);

            }

            @Override
            public DataProvider<AppUser, ?> getDataProvider() {
                return DataProvider
                        .fromCallbacks(
                                query -> appUserRepo.findByUsernameContainsIgnoreCase(filter.getValue(),
                                        new OffsetBasedPageRequest(query)).stream(),
                                query -> (int) appUserRepo.countByUsernameContainsIgnoreCase(filter.getValue()));
            }

        });
        add(gridCrud);
    }
}
