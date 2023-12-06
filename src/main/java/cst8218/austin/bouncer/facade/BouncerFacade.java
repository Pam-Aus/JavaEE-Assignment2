/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.austin.bouncer.facade;

import cst8218.austin.bouncer.facade.AbstractFacade;
import cst8218.austin.bouncer.entity.Bouncer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *Bouncer Facade class which implements methods from the abstract facade.
 * It is a child class of Abstract facade which inherits methods from the abstract facade to implement bouncer instances.
 * @author chiaustin
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
