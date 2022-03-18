/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Entidades.Lineapproveedor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thefi
 */
@Stateless
public class LineapproveedorFacade extends AbstractFacade<Lineapproveedor> {

    @PersistenceContext(unitName = "Aplicaciones2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LineapproveedorFacade() {
        super(Lineapproveedor.class);
    }
    
}
