# Spring Cloud Data Flow - Proof of Concept (POC)

## Overview
This Proof of Concept (POC) demonstrates how Spring Cloud Data Flow can be used to process batch data efficiently. The project explores key concepts such as task execution, job orchestration, and data flow management in a cloud-native environment.

## ✅ Spring Cloud Data Flow Feature Evaluation

| Requirement                                      | SCDF Support                                    | Workaround / Notes                           |
|--------------------------------------------------|----------------------------------------------------|-----------------------------------------------------|
| **Batch & Streaming Support**      | ✅ Yes: Supports **batch** (Spring Batch) and **streaming** (Spring Cloud Stream).           | Uses **Kafka, RabbitMQ, or other messaging systems** for event-driven streaming.  |
| **Configurable Transformations**         | ✅ Yes (via DSL & properties)                                  | Supports **Java, Groovy, and YAML-based configurations**.|
| **Horizontal Scalability**         | ✅ Yes                                             | Supports **Kubernetes and Cloud Foundry** for scalable deployments. [Doc](https://dataflow.spring.io/docs/feature-guides/streams/scaling/)     |
| **Self-Hosted Deployment**                             | ✅ Yes                                             | Can be deployed on-premise, **Docker, Kubernetes, or cloud platforms**.                                               |
| **End-to-End Testability**        | ✅ Yes                                        | Supports **JUnit, integration testing, and TestContainers**.   |
| **Retry Mechanism**                          | ✅ Yes                                             | Supports **automatic retries, exponential backoff, retry policies, and error-handling mechanisms via Spring Batch and Spring Cloud Task**. [Doc](https://docs.spring.io/spring-batch/reference/step/chunk-oriented-processing/retry-logic.html)|
| **Re-Runnable Workflows**                        | ✅ Yes                                             | Tasks and jobs support **restartability, checkpointing, and recovery**. [Doc](https://dataflow.spring.io/docs/feature-guides/batch/restarting/)                                             |

## Technologies Used
- **Spring Cloud Data Flow** - Orchestrates and manages batch jobs
- **Spring Batch** - Provides batch processing capabilities
- **Spring Boot** - Framework for building microservices
- **PostgreSQL / MySQL** - Database for storing batch job metadata
- **Docker & Kubernetes (Optional)** - For containerization and orchestration
- **Apache Kafka / RabbitMQ (Optional)** - Message broker for event-driven pipelines

## Architecture
Spring Cloud Data Flow follows a **modular and cloud-native** architecture, enabling batch and stream processing with high scalability and flexibility.

### Core Components
- **Spring Cloud Data Flow Server**: The central orchestrator that manages deployment, execution, and monitoring of applications.
- **Spring Cloud Skipper**: Handles the lifecycle management of streaming applications, allowing updates and rollbacks.
![image](https://github.com/user-attachments/assets/f8c1dca7-ce2f-4c08-982c-26ef69b06018)


## Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven 3+
- Docker (for containerized deployment)

## Setup Instructions
### 1. Clone the Repository
```sh
 git clone https://github.com/your-repo/spring-cloud-data-flow-poc.git
 cd spring-cloud-data-flow-poc
```

### 2. Start Spring Cloud Data Flow Server
```sh
 docker-compose up
```

### 3. Configure Spring Cloud Data Flow Shell
```sh
 java -jar spring-cloud-dataflow-shell-2.10.1.jar
 dataflow config server --uri http://localhost:9393
```

### 4. Register and Deploy Task
```sh
 app register --name my-task --type task --uri file:///tasks/jars/poc-task-1.0-SNAPSHOT.jar
 task create my-task-instance --definition "my-task"
```

### 5. Run the Task
You can run the task either from the **Spring Cloud Data Flow UI** or using the **SCDF Shell**:

**From UI:**
1. Navigate to `http://localhost:9393/dashboard`
2. Go to the `Tasks` section
3. Select `my-task-instance` and click `Launch`

<img width="1065" alt="image" src="https://github.com/user-attachments/assets/e1f85b53-8b4c-4b67-a7c0-79972217100d" />


**From SCDF Shell:**
```sh
 task launch my-task-instance
```

## Expected Output
- The batch job reads input data from a source (CSV, database, or API), processes it, and stores the transformed data.
- An `output.json` file is created inside the `output` folder with the following content:
  ```json
  [{"id":1,"name":"Alice","age":27},
   {"id":2,"name":"Bob","age":30},
   {"id":3,"name":"Charlie","age":35},
   {"id":4,"name":"David","age":40},
   {"id":5,"name":"Eve","age":45},
   {"id":6,"name":"Frank","age":50}]
  ```
- The job execution details can be monitored via the Spring Cloud Data Flow Dashboard.

## Deployment
Spring Cloud Data Flow supports **continuous deployment** for batch jobs and streaming applications.

### 1. Continuous Deployment with CI/CD
For automated deployments:
- Use **GitOps tools** like ArgoCD or FluxCD.
- Automate using **CI/CD pipelines** (GitHub Actions, Jenkins, or GitLab CI/CD).

### 2. Updating Deployed Applications
To redeploy a task application:
```sh
app register --name my-task --type task --uri file:///tasks/jars/new-task.jar --force
```

For more details, refer to [Spring Cloud Data Flow Deployment Guide](https://dataflow.spring.io/docs/batch-developer-guides/continuous-deployment/).

## Traceability
To review task logs in the Spring Cloud Data Flow (SCDF) UI, follow these steps:​

Access the SCDF Dashboard:

Navigate to the SCDF dashboard, typically at http://localhost:9393/dashboard.​
Navigate to Task Executions:

<img width="1067" alt="image" src="https://github.com/user-attachments/assets/f973e2ee-3b14-4bd7-ba77-12ed162960ff" />
<img width="1297" alt="image" src="https://github.com/user-attachments/assets/bf9594cc-b539-49f0-8291-6d51d164f81c" />

**Application Log and Deployment Log: [Link](https://docs.spring.io/spring-cloud-dataflow/docs/1.1.0.RELEASE/reference/html/_logging.html)**




## Schedule
- You can create a schedule through the SCDF UI, Shell, or REST API by specifying a cron expression that defines the execution frequency.
![image](https://github.com/user-attachments/assets/e2a7ed9d-4da5-4a2f-8532-c499d4d4d278)


For detailed instructions on scheduling tasks, refer to the [Spring Cloud Data Flow Documentation](https://dataflow.spring.io/docs/feature-guides/batch/scheduling/).



