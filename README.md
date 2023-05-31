# packer

This solution addresses a specific problem where a file path is provided, containing information about items (weight and cost), as well as the maximum weight allowed for a package that needs to be sent. When selecting items for the package, two factors are considered: minimizing the weight of the items while maximizing their cost.

---
# Solution Implemented

I have developed a Spring Boot application using TDD approach that exposes a REST API. The API endpoint is _**/api/v1/send-package**_, and it expects a request parameter 'filePath' specifying the path to a file. The API will process the file and return the response as a string, according to the specified requirements.

The implementation of the solution includes the following steps:

1. Requesting the File: The API receives the filePath parameter and retrieves the file located at the specified path.

2. File Parsing and Validation: The application includes a file parser component that performs validations on the file. This includes verifying its format, structure, and content to ensure it meets the required conditions.

3. Applying Conditions/Constraints: The parsed file is processed to meet specific conditions or constraints.
- Max weight that a package can take is ≤ 100
- There might be up to 15 items you need to choose from
- Max weight and cost of an item is ≤ 100

4. Converting to Java Readable Objects: After the file is validated, the file parser converts it into Java-readable objects. These objects represent the list of packages.

5. Generating Response: Once the file is parsed and the conditions are applied, the application generates the desired response as a string. This response contains the information about the selected items Indices in for each package.


## Application Design

I have implemented a well-structured Spring Boot application, incorporating all the necessary layers for this project. The application design follows industry best practices and includes the required layers. Abstraction has been applied effectively, where it was required.

## Data Structure
In this solution, the primary data structure utilized is the _**List**_. It is used to store both the items and the packages. The List provides a flexible and dynamic collection that allows efficient storage and retrieval of elements. By leveraging the List data structure, the solution can effectively manage and manipulate the items and packages in the problem scenario.

## Design Pattern

In this solution, the _**Singleton**_ design pattern has been employed. The Singleton pattern ensures that only a single instance of a class is created and provides a global point of access to it. By utilizing the Singleton pattern, the solution ensures that there is a single instance of a particular class that manages the items and packages, thereby facilitating centralized access and control throughout the system. This can be useful in scenarios where it is necessary to maintain a single, shared instance of an object with global scope.

## Algorithm

- The algorithm used in the solution is the _**Knapsack**_ algorithm. The _**Knapsack**_ algorithm is a dynamic programming algorithm commonly used to solve optimization problems, particularly the knapsack problem.
- In this case, the algorithm is employed to solve the problem of selecting items to be included in a package based on maximizing the cost while minimizing the weight. The items are sorted based on their cost-to-weight ratio, and then the algorithm determines the combination of items that satisfies the weight constraint while maximizing the overall cost.
- By utilizing the _**Knapsack**_ algorithm, the solution efficiently solves the problem by considering both the weight and cost of the items, ultimately selecting the optimal combination of items to be included in the package. This algorithm is well-suited for this type of optimization problem and allows for an effective solution.

## Other Info

- Used Java 11
- Open API Docs - Swagger
- Controller Advisor
- Actuator (API health check)
- Docker file (in the project main directory) to build the container image


Actuator Health API: http://localhost:8080/actuator/health


Swagger URL: http://localhost:8080/swagger-ui/index.html#/