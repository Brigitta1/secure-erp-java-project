# secure-erp-java-project
Secure ERP using MVC and DAO pattern created as a Codecool team project

Contributors:
Molnár Gabrielle
Szövetes Péter
Csalló Máté
Árvai Brigitta

Story
You work at an enterprise solution company. A new client approaches you for an ERP software they need for the administration of their daily operations. Naturally, you have multiple complex solutions for this job.

The problem is that the client is extremely suspicious of cloud technologies and the web in general. They say that what is on the net, or just on a computer that is connected to the Internet, is already compromised, and the secret services of at least four countries come and go there regularly.

So they want to see a solution that is super secure: a short and clean codebase that works on local files, strictly on offline computers. It is your team's job to create such an application from scratch.

They require a highly modular structure where the code for different content areas are separated, and every user and file I/O operation goes through one and only one channel. You decide to create a variant of the MVC (model-view-controller) architecture for terminal and local data files.

As the client doesn't provide any real data, only the general structure, you must to create some dummy data for the development.

What are you going to learn?
Collaborate with your team.

Use modular design, MVC pattern.

Search, filter, and transform data.

Write clean code.

Conform to requirements.

Collaborate with your team.

Tasks
CRM module

Implement the CRM module with basic and special operations.
Once the CRM module is selected, choosing option 1 asks the user to type the name, email, and subscription status for a new customer. When the last field is filled in, a new customer is introduced with an random ID.
Once the CRM module is selected, choosing option 2 prints all the customers.
Once the CRM module is selected, choosing option 3 asks the user for the ID of a customer. If the ID belongs to an existing customer, the user enters new values for the name, email, and subscription status. When the last field is filled in, the customer fields are updated with the given values.
Once the CRM module is selected, choosing option 4 asks the user for the ID of a customer. If the ID belongs to an existing customer, the customer is deleted from the database.
(5) Get the emails of subscribed customers.
Sales module

Implement the Sales module with basic and special operations.
(1-4) Provide basic CRUD operations.
(5) Get the transaction that made the biggest revenue.
(6) Get the product that made the biggest revenue altogether.
(7) Count the number of transactions between two dates.
(8) Sum the price of transactions between two dates.
HR module

Implement the HR module with basic and special operations.
Provide basic CRUD operations.
(5) Return the names of the oldest and the youngest employees.
(6) Return the average age of employees.
(7) Return the names of employees who have birthdays within two weeks from the input date.
(8) Return the number of employees who have at least the input clearance level.
(9) Return the number of employees per department in a Map.
General requirements
No external libraries are used, except for those already in the project.
Only Model classes use DAO classes. Model classes do not use the View at all.
