/**
 * 
 */
package com.flipkart.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Environment;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

import com.flipkart.bean.Payment;
import com.flipkart.entity.PaymentEntity;
import com.flipkart.entity.UserEntity;
import com.flipkart.exceptions.LowBalanceException;
import com.flipkart.exceptions.VersionConflictException;
import com.flipkart.exceptions.WrongCredentialsException;
import com.flipkart.utilities.HibernateUtil;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * @author HP
 *
 */
public class UserEntityDao extends AbstractDAO<UserEntity> {

	public UserEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public UserEntity viewUserData(Integer userId) {
		return get(userId);
	}

	public ArrayList<UserEntity> viewUserList() {
		System.out.println(super.currentSession());
		return (ArrayList<UserEntity>) super.currentSession().createQuery("from UserEntity").list();
	}

	public Boolean userAuthentication(String username, String password) throws WrongCredentialsException {
		Criteria criteria = super.currentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();
		if (userEntity == null) {
			throw new WrongCredentialsException("Wrong username or password!!!");
		}
		return true;
	}

	public boolean registerUser(UserEntity user) {
		Transaction transaction = super.currentSession().beginTransaction();
		Criteria criteria = super.currentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("username", user.getUsername()));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();
		if (userEntity == null) {
			super.currentSession().save(user);
			transaction.commit();
			return true;
		}
		return false;
	}

	public void makePayment(PaymentEntity payment) throws LowBalanceException {
		Transaction transaction = super.currentSession().beginTransaction();
		super.currentSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				System.out.println("Transaction isolation level is " + Environment.isolationLevelToString(connection.getTransactionIsolation()));
			}
		});
		Criteria criteria = super.currentSession().createCriteria(UserEntity.class);
		Criterion payeeData = Restrictions.eq("id", payment.getPayeeId());
		Criterion payerData = Restrictions.eq("id", payment.getPayerId());
		LogicalExpression orExp = Restrictions.or(payeeData, payerData);
		criteria.add( orExp );
		criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
		List<UserEntity> data = (List<UserEntity>) criteria.list();
		super.currentSession().refresh(data.get(0));
		super.currentSession().refresh(data.get(1));
		UserEntity payer = null,payee = null;
		for(UserEntity user:data)
		{
			System.out.println(user);
			if(user.getId()==payment.getPayeeId())
				payee = user;
			else
				payer = user;
		}
		if (payer.getBalance() < payment.getAmount())
		{
			transaction.rollback();
			throw new LowBalanceException("Payment failed due to low balance");
		}
		else {
			super.currentSession().save(payment);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			payer.setBalance(payer.getBalance() - payment.getAmount());
			payee.setBalance(payee.getBalance() + payment.getAmount());
			super.currentSession().saveOrUpdate(payee);
			super.currentSession().saveOrUpdate(payer);
			transaction.commit();
		}
	}

	public void makePaymentOp(PaymentEntity payment) throws LowBalanceException, VersionConflictException {
		Transaction transaction = super.currentSession().beginTransaction();
		super.currentSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				System.out.println("Transaction isolation level is " + Environment.isolationLevelToString(connection.getTransactionIsolation()));
			}
		});
		Criteria criteria = super.currentSession().createCriteria(UserEntity.class);
		criteria.setLockMode(LockMode.OPTIMISTIC);
		UserEntity payer = (UserEntity) criteria.add(Restrictions.eq("id", payment.getPayerId())).uniqueResult();
		UserEntity payee = null;
		if (payer.getBalance() < payment.getAmount())
		{
			transaction.rollback();
			throw new LowBalanceException("Payment failed due to low balance");
		}
		else {
			try
			{
				Criteria criteria1 = super.currentSession().createCriteria(UserEntity.class);
				payer = (UserEntity) criteria1.add(Restrictions.eq("id", payment.getPayerId())).uniqueResult();
				Criteria criteria2 = super.currentSession().createCriteria(UserEntity.class);
				payee = (UserEntity) criteria2.add(Restrictions.eq("id", payment.getPayeeId())).uniqueResult();
				super.currentSession().save(payment);
				payer.setBalance(payer.getBalance() - payment.getAmount());
				payee.setBalance(payee.getBalance() + payment.getAmount());
				super.currentSession().saveOrUpdate(payee);
				Thread.sleep(3000);
				super.currentSession().saveOrUpdate(payer);
				transaction.commit();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (Exception e)
			{
				transaction.rollback();
				throw new VersionConflictException("Version conflict");
			}
		}
		
	}
}
