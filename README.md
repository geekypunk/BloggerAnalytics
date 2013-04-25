BloggerDemoGraph Analyser
===========================

Finds blogger demographics from Amazon Common corpus data. The data extracted for analysis from the corpus is the crawled blogger profile webpage. [Sample Page](http://www.blogger.com/profile/10171345732985610861). 

Special credit to [URL index](http://commoncrawl.org/common-crawl-url-index/) by [Scott Robertson](https://angel.co/srobertson), which made the data analysis possible just in my local machine, without the need for aws.



Technology Stack
==================

1) Python script to selectively download data from common-crawl. AWS Java SDK was posing issues.

2) Maui(<http://code.google.com/p/maui-indexer/>), to extract topics from raw text. Here I used the AGROVOC vocabulary(<http://aims.fao.org/standards/agrovoc>) with Maui to extract topics .

3) JFreeChart to displaying charts


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


TODO
==============

1) Enrichment to data by crawling actual blogsites and Blogs I follow section of blogger profile.
