# Design Decisions and Rationale

### Problem Statement

#### What
MyOrg requires a standard mechanism, libraries and other components to be able to enable tracing and diagnostics across its always-growing stable of polyglot microservices.

#### Why
With Distributed services, it often becomes quite complex very quickly to pinpoint the cause of a problem, and to determine any impending failure, as Transactions criss-cross the entire gamut of microservices. With tracing enabled, bottlenecks and painpoints can be detected with very less effort, and can even be automated to some extent when triaging issues.

#### Assumptions
1. We only want to address Tracing as part of this exercise, and not other aspects, such as Overall health monitoring.

### Decisions

#### Observability Provider
##### Overview
Application Performance Monitoring (APM) is a Billion Dollar industry, with competing products from major ISVs, Public Cloud Offerings, and OpenSource. Some of them are
- Commercial: [AppDynamics](https://www.appdynamics.com/), [NewRelic](https://newrelic.com/), [DynaTrace](https://www.dynatrace.com/) etc
- Public Cloud: [AWS CloudWatch](https://aws.amazon.com/cloudwatch/), [Azure Monitor](https://docs.microsoft.com/en-us/azure/azure-monitor/overview), [Oracle OMC](https://docs.oracle.com/en/cloud/paas/management-cloud/index.html) etc
- OpenSource: [Jaeger](https://www.jaegertracing.io/), [Zipkin](https://zipkin.io/) etc

##### Chosen solution and rationale
- Solution: Zipkin
- Rationale: As this is a time-bound exercise, only cursory evaluations of the solutions could be done.
  1. Public Cloud offerings were summarily discounted owing to their portability related difficulties across cloud.
  2. OpenSource was preferred over Commercial, even though commercial offerings at least are just as good, in not better compared to the open-source alternative. The prefernce was due to the fact that there was more OpenSource community contribution towards ancillary components, which is helpful when developing a custom component. 
  3. Zipkin was decided as the solution of choice, owing to the fact that it did one thing(Tracing), and did it very well. However, using Jaeger could have been just as easy, and it's also possible to switch up the implementation if we use another layer of abstraction, e.g. OpenTelementry/OpenTracing, which can be configured to export to either.
