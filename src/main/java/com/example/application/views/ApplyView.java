package com.example.application.views;

import com.example.application.dto.UserDto;
import com.example.application.entity.*;
import com.example.application.services.*;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@AnonymousAllowed
@PageTitle("Apply")
@Route(value = "apply", layout = MainLayout.class)
public class ApplyView extends SecuredView {

    private static final Logger LOGGER = LogManager.getLogger(ApplyView.class);

    private final Checkbox dataProtectionCheckbox = new Checkbox(TranslationUtils.getTranslation("userAgreements.dataProtection"));
    private final Checkbox originalCertificateCheckbox = new Checkbox(TranslationUtils.getTranslation("userAgreements.originalCertificate"));
    private final Checkbox correctInformationCheckbox = new Checkbox(TranslationUtils.getTranslation("userAgreements.correctInformation"));
    private final Checkbox prentConsentCheckbox = new Checkbox(TranslationUtils.getTranslation("userAgreements.parentConsent"));
    private final Button applyButton = new Button(TranslationUtils.getTranslation("menuItem.apply"));

    private final UserService userService;
    private final BasicInfoService basicInfoService;
    private final EducationInfoService educationInfoService;
    private final ContactService contactService;
    private final AddressInfoService addressInfoService;
    private final RelativeInfoService relativeInfoService;

    public ApplyView(UserService userService,
                     BasicInfoService basicInfoService,
                     EducationInfoService educationInfoService,
                     ContactService contactService,
                     AddressInfoService addressInfoService,
                     RelativeInfoService relativeInfoService) {

        this.userService = userService;
        this.basicInfoService = basicInfoService;
        this.educationInfoService = educationInfoService;
        this.contactService = contactService;
        this.addressInfoService = addressInfoService;
        this.relativeInfoService = relativeInfoService;

        configureCheckBoxes();
        configureApplyButton();

        add(dataProtectionCheckbox, originalCertificateCheckbox, correctInformationCheckbox, prentConsentCheckbox, applyButton);
        setSpacing(true);
    }

    private void configureCheckBoxes() {

        dataProtectionCheckbox.addValueChangeListener(event -> checkAllChecked());
        originalCertificateCheckbox.addValueChangeListener(event -> checkAllChecked());
        correctInformationCheckbox.addValueChangeListener(event -> checkAllChecked());
        prentConsentCheckbox.addValueChangeListener(event -> checkAllChecked());
    }


    private void configureApplyButton() {

        applyButton.setEnabled(false);

        applyButton.addClickListener(event -> {
            handleApplicationSubmit();
        });
    }

    private void handleApplicationSubmit() {

        String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");

        if (isAllInformationProvided(userEmail)) {

            UserDto userDto = userService.getUserByEmail(userEmail);
            userDto.setStatus("APPLIED");
            userService.updateUser(userDto);

            Notification.show(TranslationUtils.getTranslation("notification.applicationSubmitSuccess"), 3000, Notification.Position.TOP_CENTER);

        } else {
            Notification.show(TranslationUtils.getTranslation("notification.applicationSubmitFailed"), 3000, Notification.Position.TOP_CENTER);
        }
    }

    private boolean isAllInformationProvided(String email) {

        try {
            BasicInfo basicInfo = basicInfoService.findBasicInfoByEmail(email);

            EducationInfo educationInfo = educationInfoService.findEducationInfoByEmail(email);

            List<Contact> contacts = contactService.getAllContacts(email);

            List<AddressInfo> addressInfos = addressInfoService.findAddressInfoByEmail(email);

            List<RelativeInfo> relativeInfos = relativeInfoService.findRelativeInfoByEmail(email);

            return basicInfo != null && educationInfo != null && contacts != null && addressInfos != null &&
                    relativeInfos != null && !contacts.isEmpty() && addressInfos.size() == 3 && !relativeInfos.isEmpty();

        } catch (Exception e) {

            LOGGER.error("All information not provided", e);
            return false;
        }
    }

    private void checkAllChecked() {
        boolean allChecked = dataProtectionCheckbox.getValue() &&
                originalCertificateCheckbox.getValue() &&
                correctInformationCheckbox.getValue() &&
                prentConsentCheckbox.getValue();
        applyButton.setEnabled(allChecked);
    }
}
