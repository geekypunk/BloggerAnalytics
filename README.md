BloggerDemoGraph Analyser
===========================

Finds blogger demographics from Amazon Common corpus data. The data extracted for analysis from the corpus is the crawled blogger profile webpage. [Sample Page](http://www.blogger.com/profile/10171345732985610861). 

Special credit to [URL index](http://commoncrawl.org/common-crawl-url-index/) by [Scott Robertson](https://angel.co/srobertson), which made the data analysis possible just in my local machine, without the need for aws.

Some finds
==============

1) 25% of blogger from Bangalore write about Music(Not so surprising as it is the rock capital of india)

2) NY, SF and Toronto account for 40% of the bloggers.

3) San Francisco and Dallas, each 17% account for the most bloggers with interest in Politics

4) San Francisco,Bangalore and Vancouver are among the top cities with bloggers whose interest is travel

See more report charts in SnapShots folder!

Installation
=====================

1) Import the BloggerAuthorInfo-Total.sql file into your sql server

2) Change connection credentials in ConnectionUtils.java file.

3) Run BloggerAuthorInfoAnalyser.java in src/analyser/main (Work in progress)


About
=================
This project aims at profiling blogger interests correlated with their demographics. 

Amazon's Common-Crawl corpus was used for this purpose. I extracted the crawled data corresponding to the blogger profile webpages
as the dataset for my analysis.

The selective download of the required dataset was made possible by the Common Crawl URL Index(https://github.com/trivio/common_crawl_index) by Scott Robertson(https://angel.co/srobertson)

The technology stack of the project comprises of
  1) Python script to facilitate downloading the selective chunks of ARC files in Common-Crawl corpus
  2) Maui(http://code.google.com/p/maui-indexer/) to extract topics from raw text. This was used in conjunction with AGROVOC vocabulary(http://aims.fao.org/standards/agrovoc)
  3) MySQL database is used to dump the rough results of the crawl
  4) JFreeChart is used to display charts.

Data used
-----------------
There were about 8000 blogger profile urls that were found using the URL index on the Common-Crawl corpus data. The resultant
HTML dumps of these webpages was just about 100MB. These dumps were in-turn used for analysis.

Thought process which made me arrive to this implementation stack
================================================================

Reason to use URL Index
-----------------------
Cost aversion was a factor which lead me to use the URL index, thus allowing for all the computation to be done in my local machine

Reason to use Maui
-----------------
Maui is a topic extractor having the ability to extract topic terms from text, even those which are not actually present in the text
For example, it could give me "Politics" as a topic, even though the terms politics or any of its roots not being mentioned in the text.
Maui consumes RDF based vocabularies for topic extraction.I used the latest version of AGROVOC vocabulary as it contains a broad array of topics

Reason to use blogger profile webpages
----------------------------
Since I wanted to analyse blogger interests based on demographical information, these profile pages served as a rich source of that information.
It was possible to infer about the topics which are of interest to the blogger, without actually having to crawl his entire blog.
As this was just a Proof of Concept(PoC) project, this minimed the computation cost as everthing was done locally, without the need of EC2




TODO
==============

1) Enrichment to data by crawling actual blogsites and Blogs I follow section of blogger profile.
