package com.uniovi.sdimywallapop.pageobjects;

import org.openqa.selenium.WebDriver;

public class PO_PrivateView extends PO_NavView {
    static public void refactorLogging(WebDriver driver, String email, String password) {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, email, password);
    }

    static public void refactorLogout(WebDriver driver, String text) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, text, "text", loginText);
    }
}

