/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.austin.bouncer.facade;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.ArrayList;
import cst8218.austin.bouncer.facade.BouncerFacade;
import cst8218.austin.bouncer.entity.Bouncer;

/**
 * Session Bean runs infinitely. It fetches for all bouncer instances from the
 * database and updates it's y position every time depending on the instance's y
 * velocity
 *
 * @author chiaustin
 */
@Startup
@Singleton
public class NewSessionBean {

    @EJB
    BouncerFacade bouncerFacade = new BouncerFacade(); //EJB instances for bouncer facade
    List<Bouncer> bouncers = new ArrayList<>();

    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
// the game runs indefinitely

                while (true) {

//update all the bouncers and save changes to the database
                    bouncers = bouncerFacade.findAll();  // fetch for all bouncers from database

                    // loop through all bouncer instances the update y positions as required
                    for (Bouncer bouncer : bouncers) {
                        bouncer.advanceOneFrame();
                        bouncerFacade.edit(bouncer);
                    }
//sleep while waiting to process the next frame of the animation
                    try {
// wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long) (200));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
