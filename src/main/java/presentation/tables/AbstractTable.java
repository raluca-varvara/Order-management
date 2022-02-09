package presentation.tables;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractTable<T> {
    private final Class<T> tClass;

    @SuppressWarnings("unchecked")
    public AbstractTable(){
        tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * metoda care creeaza headerul unui tabel cu Reflection
     * @param l lista de obiecte care se va pune in tabel
     * @return un vector de obiecte care vor reprezenta numele coloanelor
     */
    public Object[] columnsNames(List<T> l){
        Object[] o = new Object[tClass.getDeclaredFields().length];
        int idx = 0;
        for (Field field : tClass.getDeclaredFields()){
            o[idx]=field.getName();
            idx++;
        }
        return o;
    }

    /**
     * metoda care creeaza interiorul unui tabel
     * @param l lista cu care se va completa tabelul
     * @return matricea de obiecte care va reprezenta inetriorul tabelului
     */
    public Object[][] tableInput(List<T> l){
        int i = 0, j = 0;
        Object[][] o = new Object[l.size()][tClass.getDeclaredFields().length];
        for(T t: l) {
            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    o[i][j] = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                j++;
            }
            j=0;
            i++;
        }
        return o;
    }
}
