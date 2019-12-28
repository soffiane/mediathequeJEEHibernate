package com.cybermediatheque.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.cybermediatheque.domain.Genre;
import com.cybermediatheque.service.GenreService;

/**
 * Cette classe permet de creer un tag pour installer une combo box avec la
 * liste des genres de livre
 * 
 * @author sylar
 * @version 1.0
 */
public class GenreTag extends SimpleTagSupport {

	// ======================================
	// = Attributes =
	// ======================================
	private String value;

	// ======================================
	// = Business Methods =
	// ======================================
	public void doTag() throws JspException, IOException {

		StringBuffer buf = new StringBuffer();

		// <select>
		buf.append("<select name='genre'>");

		// Gets all the american states
		List<Genre> genres = new GenreService().listerGenre();
		for (Genre genre : genres) {

			// <option>
			buf.append("<option value='").append(genre.getCode()).append("'");

			// Selected state
			if (genre.getCode().equals(getValue())) {
				buf.append(" selected");
			}

			buf.append(">");

			// value
			buf.append(genre.getCode());

			// </option>
			buf.append("</option>");
		}

		// </select>
		buf.append("</select>");

		// Display
		getJspContext().getOut().println(buf);

	}

	// ======================================
	// = Getters and Setters =
	// ======================================
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
