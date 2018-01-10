package centmp.depotmebel.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import centmp.depotmebel.core.util.DaoException;


public class CommonDao<T> {

	private Class<T> objectClass;
	private SessionFactory sessionFactory;

	public CommonDao(Class<T> objectClass) {
		this.objectClass = objectClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean saveOrUpdate(T object) {
		boolean result = false;
		Session session = sessionFactory.openSession();
		try {
			// Session session =
			// HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(object);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			//String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
		} finally {
			session.close();
		}
		return result;
	}
	
	public boolean delete(T object) throws DaoException{
		boolean result = false;
		Session session = sessionFactory.openSession();
		try {
			// Session session =
			// HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> loadAll(Order order) {
		List<T> list = new ArrayList();
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);
			if (order!=null) {
				cr.addOrder(order);
			}
			list = cr.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> loadBy(Order order, Criterion... criterions) throws DaoException {
		List<T> list = new ArrayList();
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);

			if (order!=null) {
				cr.addOrder(order);
			}
			
			if (criterions!=null && criterions.length>0) {
				for(Criterion crit : criterions) {
					cr.add(crit);
				}
			}

			list = cr.list();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		} finally {
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> loadBy(Order order, Integer maxResult, Criterion... criterions) throws DaoException {
		List<T> list = new ArrayList();
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);

			if(order!=null) {
				cr.addOrder(order);
			}
			
			if(maxResult!=null && maxResult>0){
				cr.setMaxResults(maxResult);
			}
			
			if(criterions!=null && criterions.length>0) {
				for(Criterion crit : criterions) {
					cr.add(crit);
				}
			}

			list = cr.list();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		} finally {
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> loadBy(Order order, Integer startRow, Integer maxResult, Criterion... criterions) throws DaoException {
		List<T> list = new ArrayList();
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);

			if(order!=null) {
				cr.addOrder(order);
			}
			
			if(startRow!=null){
				cr.setFirstResult(startRow);
			}
			
			if(maxResult!=null && maxResult>0){
				cr.setMaxResults(maxResult);
			}
			
			if(criterions!=null && criterions.length>0) {
				for(Criterion crit : criterions) {
					cr.add(crit);
				}
			}

			list = cr.list();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		} finally {
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public T lastRow(String propertyName) throws DaoException{
		//List<T> list = new ArrayList<>();
		T object = null;
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);
			cr.addOrder(Order.desc(propertyName));
			cr.setMaxResults(1);
			List<T> list = cr.list();
			if(list.size()>0){
				object = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		} finally {
			session.close();
		}
		return object;
	}
	
	public Integer rowCount(Criterion... criterions) throws DaoException{
		Integer result = 0;
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(objectClass);
			cr.setProjection(Projections.rowCount());
			
			if(criterions!=null) {
				for(Criterion crit : criterions) {
					cr.add(crit);
				}
			}
			
			List<?> list = cr.list();
			if(list.size()>0){
				if(list.get(0) instanceof Long){
					Long r = (Long) list.get(0);
					result = r.intValue();
				}else {
					result = (Integer) list.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getCause()!=null?e.getCause().getMessage():e.getLocalizedMessage();
			throw new DaoException(message);
		}finally {
			session.close();
		}
		
		return result;
	}
	
	public List<?> nativeQuery(String sqlQuery){
		List<?> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try {
			Query query =  session.createSQLQuery(sqlQuery);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}
