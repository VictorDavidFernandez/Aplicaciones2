package Controladores;

import Entidades.Pedidoproveedor;
import Controladores.util.JsfUtil;
import Controladores.util.PaginationHelper;
import Entidades.Lineapproveedor;
import Entidades.Proveedor;
import Repositorios.PedidoproveedorFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("pedidoproveedorController")
@SessionScoped
public class PedidoproveedorController implements Serializable {

    private Pedidoproveedor current;
    private DataModel items = null;
    @EJB
    private Repositorios.PedidoproveedorFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Proveedor proveedor;
    private List<Pedidoproveedor> pedidosProveedor;
    private SelectItem[] desplegablePedidos;
    private Pedidoproveedor pedidoProveedor;
    private List<Lineapproveedor> lineasPedido;

    public List<Lineapproveedor> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<Lineapproveedor> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }
    
    public Pedidoproveedor getPedidoProveedor() {
        return pedidoProveedor;
    }

    public void setPedidoProveedor(Pedidoproveedor pedidoProveedor) {
        this.pedidoProveedor = pedidoProveedor;
    }

    public SelectItem[] getDesplegablePedidos() {
        return desplegablePedidos;
    }

    public void setDesplegablePedidos(SelectItem[] desplegablePedidos) {
        this.desplegablePedidos = desplegablePedidos;
    }

    public List<Pedidoproveedor> getPedidosProveedor() {
        return pedidosProveedor;
    }

    public void setPedidosProveedor(List<Pedidoproveedor> pedidosProveedor) {
        this.pedidosProveedor = pedidosProveedor;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public PedidoproveedorController() {
    }

    public Pedidoproveedor getSelected() {
        if (current == null) {
            current = new Pedidoproveedor();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PedidoproveedorFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Pedidoproveedor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Pedidoproveedor();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoproveedorCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Pedidoproveedor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoproveedorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Pedidoproveedor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoproveedorDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public SelectItem[] cargarDesplegablePedidos() {
        if(pedidosProveedor != null)
            return desplegablePedidos = getSelectItems(pedidosProveedor, true);
        return null;
    }

    public Pedidoproveedor getPedidoproveedor(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pedidoproveedor.class)
    public static class PedidoproveedorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidoproveedorController controller = (PedidoproveedorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidoproveedorController");
            return controller.getPedidoproveedor(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pedidoproveedor) {
                Pedidoproveedor o = (Pedidoproveedor) object;
                return getStringKey(o.getNumPedido());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Pedidoproveedor.class.getName());
            }
        }

    }
    public void cargarPedidosProveedor() {
        if(proveedor!=null)
            pedidosProveedor = ejbFacade.pedidosProveedor(proveedor);
    }
    public static SelectItem[] getSelectItems(List<Pedidoproveedor> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("-1", "Selecciona un pedido");
            i++;
        }
        for (Pedidoproveedor x : entities) {
            items[i++] = new SelectItem(x, x.getNumPedido().toString()/*+ " " + x.getApellido2() + ", " + x.getNomAutor()*/);
        }
        return items;
    } 
    public void cargarLineasPedido() {
        if(pedidoProveedor!=null)
            lineasPedido = ejbFacade.pedidosProveedorLineas(pedidoProveedor);
    }
}
