package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.envers.AuditReaderFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {
    
    public List<User> getHistory() {
        return AuditReaderFactory.get(this.getEntityManager())
                .createQuery()
                .forRevisionsOfEntity(User.class, true,true)
                .getResultList();
    }
}
