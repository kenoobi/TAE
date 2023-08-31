# TAE

To reproduce what I have done, just replace the POM.xml file and PetStoreTest.java in the Test folder of the project.

After this run your project locally and run the PetStoreTest class.

I have designed a test plan for the following methods.
For practical purposes, real (1.10), fictitious (9999) and simulated (123) identifiers have been used.

PUT: /pet - Update an existing pet:
Scenario 1: Successful update of the existing pet
Scenario 2: Update a non-existent pet (verify status code)
Scenario 3: Update with invalid data (verify validation)

POST: /pet - Add a new pet to the store:
Scenario 1: Successful addition of a new pet
Scenario 2: Addition with invalid data (verify validation)
Scenario 3: Adding a duplicate pet (verify status code)

GET: /pet/findByStatus - Finds Pets by status:
Scenario 1: Search for pets by valid status (for example, "available")
Scenario 2: Search for pets by invalid status (verify status code)
Scenario 3: Search without specifying the status (check status code)

GET: /pet/findByTags - Finds Pets by tags:
Scenario 1: Search for pets by valid tags
Scenario 2: Search for pets by invalid tags (verify status code)

GET: /pet/{petId} - Find pet by ID:
Scenario 1: Successful search for a pet by valid ID
Scenario 2: Search for a pet with a non-existent ID (verify status code)
Scenario 3: Search for a pet with invalid ID (verify status code)

POST: /pet/{petId} - Updates a pet in the store with form data:
Scenario 1: Successful update of a pet with valid data
Scenario 2: Update a non-existent pet (verify status code)
Scenario 3: Update with invalid data (verify validation)

DELETE: /pet/{petId} - Deletes a pet:
Scenario 1: Successful removal of a pet by valid ID
Scenario 2: Delete a pet with a non-existent ID (verify status code)

POST: /pet/{petId}/uploadImage - uploads an image: Dowload a Imagen and change the Path in the test.
Scenario 1: Successful upload of an image for a pet by valid ID
Scenario 2: Uploading an image for a pet with a non-existent ID (verify status code)
Scenario 3: Upload an invalid image (verify validation)

Coverage has been taken into account, thus covering the most common responses when sending a request.
