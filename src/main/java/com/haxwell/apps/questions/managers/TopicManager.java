package com.haxwell.apps.questions.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.haxwell.apps.questions.constants.Constants;
import com.haxwell.apps.questions.entities.Topic;

public class TopicManager extends Manager {

	public static Collection<Topic> getTopicsById(String csvString) {
		EntityManager em = emf.createEntityManager();
		
		String strQuery = "SELECT * FROM topic WHERE id=";
		StringTokenizer tokenizer = new StringTokenizer(csvString, ",");
		
		while (tokenizer.hasMoreTokens())
		{
			strQuery += tokenizer.nextToken();
			
			if (tokenizer.hasMoreTokens())
				strQuery += " OR id=";
		}
				
		Query query = em.createNativeQuery(strQuery, Topic.class);
		
		Collection<Topic> coll = query.getResultList();
		
		em.close();
		
		return coll;
	}
	
	public static Topic getTopicById(int i)
	{
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNativeQuery("SELECT * FROM topic WHERE id = ?1", Topic.class);
		
		query.setParameter(1, i);
		
		List<Topic> list = (List<Topic>)query.getResultList();
		
		em.close();

		return (list.size() > 0 ? list.get(0) : null);
	}
	
	public static Topic getTopic(String text)
	{
		EntityManager em = emf.createEntityManager();
		
		// Look around the database, is there an existing Topic?
		Query query = em.createNativeQuery("SELECT * FROM topic WHERE text = ?1", Topic.class);
		
		query.setParameter(1, text);
		
		List<Topic> list = (List<Topic>)query.getResultList();
		
		em.close();

		return (list.size() > 0 ? list.get(0) : null);
	}
	
	// TODO: Create a standard getAll() method for Managers in general
	public static Collection<Topic> getAllTopics() {
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT t FROM Topic t");
		
		Collection<Topic> rtn = (Collection<Topic>)query.getResultList(); 
		
		em.close();
		
		return rtn;
	}

	public static Integer getNumberOfQuestionsForTopic(Topic topic) {
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNativeQuery("SELECT count(*) FROM question_topic WHERE topic_id = ?1", Integer.class);
		
		query.setParameter(1, topic.getId());
		
		Integer rtn = (Integer)query.getSingleResult();
		
		em.close();
		
		return rtn;
	}
	
	public static Collection<Topic> getAllTopicsWithMoreThanXXQuestions(int min)
	{
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNativeQuery("SELECT qt.topic_id, count(question_id) FROM question_topic qt group by topic_id");
		
		Collection<Topic> rtn = new ArrayList<Topic>();
		
		List list = query.getResultList();
		
		for (int i = 0; i < list.size(); i++) {
			
			Object[] o = (Object[])list.get(i);
			
			int topicId = Integer.parseInt(o[0].toString());
			int count = Integer.parseInt(o[1].toString());
			
			if (count > min)
				rtn.add(getTopicById(topicId));
		}
		
		em.close();
		
		return rtn;
	}
	
	public static void delete(Set<Topic> topics, Topic t)
	{
		topics.remove(t);
	}
}
