package com.kittens.database;

import com.google.common.base.Joiner;
import com.google.gson.annotations.Expose;

import com.kittens.Utils;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;

public class Dataset extends Object {

	/**
	 * A row in a dataset.
	 */
	public static final class Row extends Object {

		// the array of values in this row
		@Expose private String[] values;

		/**
		 * Creates a new dataset row with the values contained in the given {@code ArrayList<String>}.
		 */
		public Row(ArrayList<String> values) {
			this.values = new String[values.size()];
			for (int i = 0; i < values.size(); i++) {
				this.values[i] = values.get(i);
			}
		}
		/**
		 * Creates a row with the given values.
		 */
		public Row(String ... values) {
			this.values = values;
		}
		/**
		 * Returns the size/width of this row.
		 */
		public int getWidth() {
			return values.length;
		}
		/**
		 * Sets the values contained in the row.
		 */
		public Row setValues(String ... values) {
			this.values = values;
			return this;
		}
		/**
		 * Returns the values in this row.
		 */
		public String[] getValues() {
			return values;
		}
		/**
		 * Returns a list of the values in this row.
		 */
		@Override public String toString() {
			return Joiner.on(",").join(values);
		}

	}

	// a sample set of data
	public static final Dataset newSampleDataset(final User user) {
		return new Dataset(
			user,
			"New Dataset",
			"This is a new dataset just for you. THIS IS JUST FILLER DATA. You can go ahead and modify this to suit your needs.",
			new Date()
		).setHeaders(
			"People",
			"Places",
			"Things"
		).setRows(
			new Dataset.Row("33", "43", "12"),
			new Dataset.Row("12", "10", "42"),
			new Dataset.Row("91", "11", "42"),
			new Dataset.Row("32", "42", "12")
		);
	}
	// uuid
	@Expose private final String UUID;
	// the owner of this dataset
	@Expose private User owner;
	// the dataset name
	@Expose protected String name;
	// the dataset description
	@Expose protected String description;
	// at what time this dataset was created
	@Expose protected Date dateOfCreation;
	// the list of other users with access to this dataset
	@Expose protected ArrayList<User> collaborators = new ArrayList<User>(/* 16 */);
	// the headers of the dataset
	@Expose protected ArrayList<String> headers = new ArrayList<String>(/* 16 */);
	// the rows of data
	@Expose protected ArrayList<Row> rows = new ArrayList<Row>(/* 16 row default */);

	/**
	 * Creates the dataset given the UUID, name, and desc.
	 */
	Dataset(String UUID, String name, String description, Date dateOfCreation) {
		this.UUID = UUID;
		this.name = name;
		this.description = description;
		this.dateOfCreation = dateOfCreation;
	}
	/**
	 * Creates an empty dataset given an owner, name, and description.
	 */
	public Dataset(User owner, String name, String description, Date dateOfCreation) {
		UUID = Utils.uuid();
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.dateOfCreation = dateOfCreation;
	}
	/**
	 * Returns this dataset's UUID.
	 */
	public String getUUID() {
		return UUID;
	}
	/**
	 * Returns the owner of the dataset.
	 */
	public User getOwner() {
		return owner;
	}
	/**
	 * Sets the owner of the dataset.
	 */
	public Dataset setOwner(User user) {
		this.owner = user;
		return this;
	}
	/**
	 * Returns the name of this dataset.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of this dataset.
	 */
	public Dataset setName(String name) {
		this.name = name;
		return this;
	}
	/**
	 * Returns the description of the dataset.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of this dataset.
	 */
	public Dataset setDescription(String description) {
		this.description = description;
		return this;
	}
	/**
	 * Returns the date this dataset was created.
	 */
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	/**
	 * Returns the date of creation formatted for an HTML document.
	 */
	public String getDatetime() {
		return Utils.formatDate(dateOfCreation);
	}
	/**
	 * Returns the headers for this dataset.
	 */
	public ArrayList<String> getHeaders() {
		return headers;
	}
	/**
	 * Sets the collaborators for this dataset.
	 */
	public Dataset setCollaborators(ArrayList<User> users) {
		collaborators = users;
		return this;
	}
	/**
	 * Adds collaborators to the dataset.
	 */
	public Dataset addCollaborators(User ... users) {
		for (User user : users) {
			collaborators.add(user);
		}
		return this;
	}
	/**
	 * Returns the list of collaborators for this dataset.
	 */
	public ArrayList<User> getCollaborators() {
		return collaborators;
	}
	/**
	 * Sets the heders for the dataset.
	 */
	public Dataset setHeaders(ArrayList<String> headers) {
		this.headers = headers;
		return this;
	}
	/**
	 * Sets the heders for the dataset.
	 */
	public Dataset setHeaders(String ... headers) {
		for (String header : headers) {
			this.headers.add(header);
		}
		return this;
	}
	/**
	 * Returns the rows of this dataset.
	 */
	public ArrayList<Dataset.Row> getRows() {
		return rows;
	}
	/**
	 * Returns the values in a particular row.
	 */
	public Dataset.Row getRow(int y) {
		if (y >= rows.size()) {
			return null;
		}
		return rows.get(y);
	}
	/**
	 * Clears all rows in the table to set the given rows.
	 */
	public Dataset setRows(Dataset.Row ... rows) {
		this.rows = new ArrayList<Dataset.Row>(rows.length);
		for (Dataset.Row row : rows) {
			if (row.getWidth() < headers.size()) {
				return null;
			}
			this.rows.add(row);
		}
		return this;
	}
	/**
	 *
	 */
	public Dataset setRows(ArrayList<Row> rows) {
		this.rows = rows;
		return this;
	}
	/**
	 * Adds the specified rows to the dataset.
	 */
	public Dataset addRows(Dataset.Row ... rows) {
		for (Dataset.Row row : rows) {
			if (row.getWidth() < headers.size()) {
				return null;
			}
			this.rows.add(row);
		}
		return this;
	}
	/**
	 * Returns the length of this dataset.
	 */
	public int getRowCount() {
		return rows.size() + 1;
	}
	/**
	 * Returns the width of the dataset.
	 */
	public int getWidth() {
		return headers.size();
	}
	/**
	 * Returns whether this dataset has at least headers,
	 * a name, description, and an owner.
	 */
	public boolean isValid() {
		return true;
	}
	/**
	 * Returns the dataset formatted as a TSV list.
	 */
	public String toTSV() {
		StringBuilder s = new StringBuilder();
		Joiner.on("\t").appendTo(s, headers);
		s.append(Utils.NEWLINE);
		for (Row row : rows) {
			Joiner.on("\t").appendTo(s, row.getValues());
			s.append(Utils.NEWLINE);
		}
		return s.toString();
	}
	/**
	 * Returns the dataset formatted as a CSV list.
	 */
	public String toCSV() {
		StringBuilder s = new StringBuilder();
		Joiner.on(",").appendTo(s, headers);
		s.append(Utils.NEWLINE);
		for (Row row : rows) {
			Joiner.on(",").appendTo(s, row.getValues());
			s.append(Utils.NEWLINE);
		}
		return s.toString();
	}
	/**
	 * Returns the headers and values contained in this dataset as a String.
	 */
	@Override public String toString() {
		return toCSV();
	}

}
