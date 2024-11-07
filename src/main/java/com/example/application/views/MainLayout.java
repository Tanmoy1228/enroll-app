package com.example.application.views;

import com.example.application.services.UserService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainLayout extends AppLayout {

    private static final Logger LOGGER = LogManager.getLogger(MainLayout.class);

    private final UserService userService;

    public MainLayout(UserService userService) {

        this.userService = userService;

        setPrimarySection(Section.DRAWER);
        addToDrawer(createDrawerContent());
        addToNavbar(true, createHeader());
    }

    private HorizontalLayout createHeader() {

        FlagsLayout flagsLayout = new FlagsLayout();
        ProfileLayout profileInfoLayout = new ProfileLayout(userService);

        VerticalLayout flagsAndProfileLayout = new VerticalLayout(flagsLayout, profileInfoLayout);
        flagsAndProfileLayout.setSpacing(false);
        flagsAndProfileLayout.setPadding(false);
        flagsAndProfileLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.getStyle().set("margin-right", "80%");

        HorizontalLayout header = new HorizontalLayout(drawerToggle, flagsAndProfileLayout);
        header.setId("header");
        header.setWidthFull();
        header.setSpacing(false);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("padding", "10px").set("border-bottom", "5px solid #002f6c");

        return header;
    }

    private Component createDrawerContent() {

        VerticalLayout layout = new VerticalLayout();

        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/ala-too-logo.png", ""));
        logoLayout.getStyle().set("margin-top", "10px").set("margin-left", "10px");

        Tabs menus = createMenus();
        layout.add(logoLayout, menus);

        return layout;
    }

    private Tabs createMenus() {

        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

        Tab[] menuItems = createMenuItems();
        tabs.add(menuItems);

        Integer selectedIndex = (Integer) VaadinSession.getCurrent().getAttribute("selectedTabIndex");

        if (selectedIndex != null && selectedIndex >= 0 && selectedIndex < menuItems.length) {
            tabs.setSelectedIndex(selectedIndex);
            styleSelectedTab(menuItems[selectedIndex]);
        }

        tabs.addSelectedChangeListener(event -> {

            for (Tab tab : menuItems) {
                tab.getStyle().remove("color").remove("background-color");
            }

            styleSelectedTab(event.getSelectedTab());
            VaadinSession.getCurrent().setAttribute("selectedTabIndex", tabs.getSelectedIndex());
        });

        return tabs;
    }

    private void styleSelectedTab(Tab selectedTab) {
        selectedTab.getStyle().set("background-color", "#005b9a").set("color", "white");
    }

    private Tab[] createMenuItems() {
        return new Tab[] {
                createTab(TranslationUtils.getTranslation("menuItem.main"), BasicInformationView.class),
                createTab(TranslationUtils.getTranslation("menuItem.education"), EducationInformationView.class),
                createTab(TranslationUtils.getTranslation("menuItem.contact"), ContactInformationView.class),
                createTab(TranslationUtils.getTranslation("menuItem.relative"), RelativesInformationView.class),
                createTab(TranslationUtils.getTranslation("menuItem.apply"), ApplyView.class),
                createLogoutTab()
        };
    }

    private Tab createLogoutTab() {
        Tab logoutTab = new Tab(TranslationUtils.getTranslation("menuItem.logout"));
        logoutTab.getElement().addEventListener("click", event -> logout());
        logoutTab.getStyle().set("font-size", "20px").set("font-weight", "bold").set("margin-top", "50px").set("cursor", "pointer");
        return logoutTab;
    }

    private void logout() {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        getUI().ifPresent(ui -> ui.getPage().setLocation("/login"));
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {

        Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        tab.getStyle().set("font-size", "20px").set("font-weight", "bold").set("margin-top", "20px");
        return tab;
    }
}
