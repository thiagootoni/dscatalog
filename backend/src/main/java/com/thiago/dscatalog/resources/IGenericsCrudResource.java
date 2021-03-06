package com.thiago.dscatalog.resources;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IGenericsCrudResource<T, ID> {

	public ResponseEntity<Page<T>> findAllPaginate(Integer page,  Integer linesPerPage, String orderBy, String direction);
	
	public ResponseEntity<T> findOne(ID id);
	
	public ResponseEntity<T> insertOne(T obj);
	
	public ResponseEntity<T> updateOne(T obj, ID id);
	
	public ResponseEntity<T> deleteOne(ID id);
	
}
