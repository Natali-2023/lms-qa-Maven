package de.aittr.lms.UITests;

import de.aittr.lms.CSVDataProviders;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupModuleLessonInfoTests extends TestBaseUI{
        @BeforeMethod
        public void precondition() {
            app.getUserUI().loginWithData("admin@mail.com", "Admin123!");
            app.getUserUI().closeLoginMessage();
        }

        @Test(dataProvider = "provideGetGroupModuleLessonData", dataProviderClass = CSVDataProviders.class)
        public void isAllElementsInGroupTest(String group,String module, String lesson) {
            app.getUserUI().clickOnLessonsInSideBar();

            app.getUserUI().selectGroup(group);
            System.out.println("********************************************************************");
            System.out.println("                       Group: " + group);
            System.out.println("********************************************************************");

            if (app.getUserUI().isModulePresent()) {
                app.getUserUI().selectModule(module);
                System.out.println("--------------------------------------------------------------------");
                System.out.println("                       Module: " + module);
                System.out.println("--------------------------------------------------------------------");
                System.out.println("     Lesson   |  Plan  |  Theory  |  Home work  |  Code  |  Video  |");
                System.out.println("--------------------------------------------------------------------");

                app.getUserUI().selectLesson(lesson);

                    String plan = "o";
                    String theory = "o";
                    String homeWork = "o";
                    String code = "o";
                    String video = "o";

                    if (app.getUserUI().isPlanLinePresent()) {
                        // Проверяем вкладку ПЛАН и наличие там текста
                        app.getUserUI().clickOnPlanLine();
                        if (app.getUserUI().isTextPresent()) {
                            plan = "+";
                        } else {
                            plan = "-";
                        }
                        app.getUserUI().clickOnPlanLine();
                        app.getUserUI().pause(1000);
                    }

                    if (app.getUserUI().isTheoryLinePresent()) {
                        // Проверяем вкладку Theory
                        app.getUserUI().clickOnTheoryLine();
                        if (app.getUserUI().isTextPresent()) {
                            theory = "+";
                        } else {
                            theory = "-";
                        }
                        app.getUserUI().clickOnTheoryLine();
                        app.getUserUI().pause(1000);
                    }

                    if (app.getUserUI().isHomeWorkLinePresent()) {
                        // Проверяем вкладку Домашка
                        app.getUserUI().clickOnHomeWorkLine();
                        if (app.getUserUI().isTextPresent()) {
                            homeWork = "+";
                        } else {
                            homeWork = "-";
                        }
                        app.getUserUI().clickOnHomeWorkLine();
                        app.getUserUI().pause(1000);
                    }

                    if (app.getUserUI().isCodeLinePresent()) {
                        //Проверяем наличие кода
                        app.getUserUI().clickOnCodeLine();
                        if (app.getUserUI().isCodePresent()) {
                            code = "+";
                        } else {
                            code = "-";
                        }
                        app.getUserUI().clickOnCodeLine();
                        app.getUserUI().pause(1000);
                    }

                    if (app.getUserUI().isVideoLinePresent()) {
                        // Проверяем наличие видео
                        app.getUserUI().clickOnVideoLine();
                        // Использование метода для поиска видеоэлементов
                        List<WebElement> videoElements = app.getUserUI().findVideoElements();
                        // Проверьте, что найдено хотя бы одно видео
                        if (videoElements.isEmpty()) {
                            video = "-";
                        } else {
                            video = "" + videoElements.size();

                          /* выводит src каждого видео:
                            for (WebElement videoElement : videoElements) {
                                String videoSource = videoElement.getAttribute("src");
                                System.out.println("src-видео = " + videoSource);
                               assert videoSource != null && !videoSource.isEmpty() : "Отсутствует атрибут 'src' у видео";
                                System.out.println(videoSource);
                            }*/
                        }
                        app.getUserUI().clickOnVideoLine();
                        app.getUserUI().pause(1000);
                    }
                    System.out.printf("    %-5s |   %-5s|    %-5s |      %-5s  |   %-5s|    %-5s%n", lesson, plan, theory, homeWork, code, video + "    |");
            } else {
                System.out.println("В данной группе модулей еще нет");
            }
            app.getUserUI().scrollPageUp();
        }
    }