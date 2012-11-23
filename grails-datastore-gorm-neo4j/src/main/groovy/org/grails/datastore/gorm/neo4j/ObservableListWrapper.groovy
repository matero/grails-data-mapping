package org.grails.datastore.gorm.neo4j

import java.beans.PropertyChangeEvent
import org.grails.datastore.mapping.core.Session
import org.grails.datastore.mapping.collection.PersistentList

import java.beans.PropertyChangeListener

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: 18.09.11
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
class ObservableListWrapper implements List, Externalizable {

    @Delegate List wrapped
    def entity
    PropertyChangeListener propertyChangeListener
    Class clazz

    ObservableListWrapper(def entity, Collection keys, Class clazz, Session session) {
        this.entity = entity
        this.propertyChangeListener = session
        this.clazz = clazz
        wrapped = new PersistentList(keys, clazz, session)
    }

    ObservableListWrapper() {
    }

    private fireChangeEvent() {
        propertyChangeListener.propertyChange(new PropertyChangeEvent(entity, null, null, null))
    }

    boolean add(obj) {
        fireChangeEvent()
        wrapped.add(obj)
    }

    boolean remove(def obj) {
        fireChangeEvent()
        wrapped.remove(obj)
    }

    boolean addAll(Collection coll) {
        fireChangeEvent()
        wrapped.addAll(coll)
    }

    boolean retainAll(Collection coll) {
        fireChangeEvent()
        wrapped.retainAll(coll)
    }

    boolean removeAll(Collection coll) {
        fireChangeEvent()
        wrapped.removeAll(coll)
    }

    void clear() {
        fireChangeEvent()
        wrapped.clear()
    }

    public Object set(int index, Object element) {
        fireChangeEvent()
        wrapped.set(index, element)
    }

    public void add(int index, Object element) {
        fireChangeEvent()
        wrapped.add(index, element)
    }

    public Object remove(int index) {
        fireChangeEvent()
        wrapped.remove(index)
    }

    public boolean addAll(int index, Collection c) {
        fireChangeEvent()
        wrapped.addAll(index, c)
    }

    void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
         objectOutput.writeObject()
         def collection = new ArrayList(wrapped)
         objectOutput.writeObject(collection)
     }

     void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException, java.lang.ClassNotFoundException {
         clazz = objectInput.readObject()
         List collection = objectInput.readObject()
         wrapped = new PersistentList(clazz, null, collection)

     }


}
