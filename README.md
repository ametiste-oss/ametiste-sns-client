# Ametiste SNS client

## Build Status

**CI Statuses**  

[![Build Status](https://travis-ci.org/ametiste-oss/ametiste-sns-client.svg?branch=master)](https://travis-ci.org/ametiste-oss/ametiste-sns-client)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/12fe82ab1e3e43b3b34f4e7aa4924b81)](https://www.codacy.com/app/Ametiste-OSS/ametiste-sns-client)
[![codecov.io](https://codecov.io/github/ametiste-oss/ametiste-sns-client/coverage.svg?branch=master)](https://codecov.io/github/ametiste-oss/ametiste-sns-client?branch=master)

**Latest Stable**  
  
[![Download](https://api.bintray.com/packages/ametiste-oss/maven/sns-client-starter/images/download.svg?version=0.1.0-RELEASE)](https://bintray.com/ametiste-oss/maven/sns-client-starter/0.1.0-RELEASE)

**Current Development** 
  
[ ![Download](https://api.bintray.com/packages/ametiste-oss/maven/sns-client-starter/images/download.svg) ](https://bintray.com/ametiste-oss/maven/sns-client-starter/_latestVersion)

##Overview
Ametiste SNS client is simple notification service's client whose goal is any events of interest registration.

##Documentation 
Library at a glance is described here, for more detailed description view [wiki](https://github.com/ametiste-oss/ametiste-sns-client/wiki)(to be done)

##Usage example

```
		@Autowired
		private org.ametiste.sns.client.creators.InjectingReportCreator creator;
	
		org.ametiste.sns.client.creators.injectors.model.ReportData data = injector -> {
			injector.injectId(UUID.randomUUID());
			injector.injectType(REPORT_TYPE);
			event.getEntrySet().forEach(entry ->
					injector.injectContextEntry(entry.getKey(), entry.getValue()));
		};

		creator.createReport(data);
```

##Description 

SNS client library provides client code with creator, that registers any events of interest in form of object, commonly called by library as report. Report should contain id, type, sender and date, and may contain any context data of client's interest. All fields are to be described more detailed. 

##Binaries
All non experimental dependencies is accessible at bintray central.

####Usage snippets

#####Gradle

```
repositories {
     mavenCentral()
     jcenter()
}
compile ""org.ametiste.sns:sns-client-starter:${snsClientVersion}"
```

#####Maven

```
<repositories>
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>central</id>
      <name>bintray</name>
      <url>http://jcenter.bintray.com</url>
    </repository> 
</repositories>

<dependency>
	<groupId>org.ametiste.sns</groupId>
	<artifactId>sns-client-starter</artifactId>
	<version>snsClient.version</version>
</dependency>
```

##Configuration 

SNS client requires two properties, that have no defaults: 
- *org.ametiste.sns.host* - host of SNS server 
- *org.ametiste.sns.sender* - identifying name of service that uses sns library, preferable to be unique for service 

It also can be tuned with other optional properties: 
- *org.ametiste.sns.namespace* - namespace of client, mostly in boundaries of metrics, defaulted to 'sns'
- *org.ametiste.sns.relativePath* - default is '', however required if SNS server is not launched on root location. 
- *org.ametiste.sns.capacity* - capacity of driver queue, defaulted to 1000, if exceed, no further reports are accepted to be sent before queue is in bounds of capacity again. 
- *org.ametiste.sns.thread.name* - name of thread responsible for reports sending, defaulted to 'snsReports-'
- *org.ametiste.sns.thread.number* - number of threads responsible for reports sending, defaulted to 1,
