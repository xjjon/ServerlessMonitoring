# Monitoring - Serverless Service

### Overview

Simple monitoring service that leverages [serverless framework](https://github.com/serverless/serverless).

The goals of this service is to:

* Provide a simple interface to monitor website health
* Provide a low-overhead and cost effective solution

Uses the following Amazon Web Services (AWS) services:

Lambda
CloudFormation
CloudWatch
IAM
S3
SES (optional)
SNS (optional)

The monitor will check status at the configured interval and assert the expected status. 
If the assert fails then a notification will be generated.

### Quick Start

1) Configure `custom` values in `serverless.yml`.
2) Configure your monitors in `monitors.json`.
    ```
        [
          {
            "url": "https://www.jonathanyu.xyz",
            "expectedStatusCode": 200
          },
          {
            "url": "https://www.github.com",
            "expectedStatusCode": 200
          }
        ]
    ```