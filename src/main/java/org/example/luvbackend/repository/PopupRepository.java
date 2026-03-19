package org.example.luvbackend.repository;

import java.util.List;

import org.example.luvbackend.entity.popup.Popup;
import org.example.luvbackend.exception.popup.PopupException;
import org.example.luvbackend.exception.popup.PopupExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupRepository extends MongoRepository<Popup, String> {
	List<Popup> findByIsShow(boolean isShow);

	default Popup findByIdOrElseThrow(String popupId) {
		return findById(popupId)
			.orElseThrow(() -> new PopupException(PopupExceptionCode.NOT_FOUND_POPUP));
	}
}
