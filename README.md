### Project 2: Social Network Analysis with MapReduce (v 1.1)

In this project, we will analyze a large dataset of user comments from popular news aggregation website Reddit. On Reddit, members submit content including news stories, articles, images, or videos and are also allowed to moderate the site via voting submissions up or down. The site is organized into a multitude of subreddits that specialize in particular types of content or discussion. For example, /r/politics covers US politics, and /r/technology focuses on tech news.

Similar to the content submissions, comments can also be voted up or down and several other metadata items are tracked. You’ll use these features to help you write MapReduce jobs that filter, aggregate, and glean insights from the dataset. You must use Java for this assignment, but you are given more leeway on using 3rd party libraries; for instance, since the dataset is in JSON format you may incorporate a JSON parser into your codebase. However, libraries that trivialize the assignment (anything that implements MapReduce-related functionality) are not allowed. For plotting or visualizations, you are not required to use Java (matplotlib, R, or even Excel is fine there). If in doubt, **ask first**.

Some students are familiar with Reddit while others may not be. As you explore the dataset, feel free to ask questions on Piazza or in your discussion groups. It’s also worth noting that since Reddit originated in the US, the comments and submissions will likely trend towards being US-centric. Additionally, certain demographics may be under- or over-represented by the dataset. You should keep factors such as these in mind as you perform your analysis.

#### Dataset Location
You can find the files in /bigdata/mmalensek/data/ on orion01. This dataset was sourced from here.

#### Deliverables
You will submit three deliverables for Project 2:

 * Code for your MapReduce jobs
 * A **notebook** in Markdown format containing your answers to the questions below and your thoughts/opinions/analysis.
 * A project retrospectiv
 
You will likely produce produce several small MapReduce jobs in this project. Each of the tasks below can be broken up into several jobs, or you can combine some of them. However, I should be able to run your code in “one shot,” i.e., if I want the answer to a certain question, I should be able to launch the appropriate job via YARN and see its results without needing to run other stages or scripts.

As usual, some aspects of these questions are left up to your own interpretation. Occasionally there are no right/wrong answers, but you should be able to justify your approach.

_Important_: many of the questions are best answered with **context**. Think of it this way: if I ask you for a location, you may want to embed a map in your notebook or provide some statistics about it (population, nearby landmarks). Perhaps the question involves some obscure concept or subculture; in that case, a link to the appropriate Wikipedia article is useful. Combining different forms of media through data fusion can tell a compelling story (…just make sure the story isn’t misleading!) Please also include **how long** each MapReduce job ran for. Failure to include necessary contextual information will result in deductions, regardless if you found the correct answer.

One final note: many of the questions below ask for you to find a specific user (or group of users). Be wary of bots or automated (non-human) accounts; perhaps the user that wrote the most comments in a particular time frame was just a bot that posts advertisements – in some cases, you will want to ignore these, so finding the top N users could be a better approach than finding the absolute top user.

#### Warm-up
 * **[0.25 pt]** How many records are in the dataset?
 * **[0.25 pt]** How many unique subreddits are there?
 * **[0.5 pt]** What user wrote the most comments in July of 2012? What was the user’s top three most-upvoted comments?
 * **[1 pt]** Choose a day of significance to you (e.g., your birthday), and retrieve a 5% sample of the comments posted on this particular day across all 5 years of the dataset.
* **[1 pt]** The number of comments posted per year will likely trend upward over time as more users join Reddit. Use feature scaling to normalize the number of comments per month from 0.0 to 1.0 and plot the values for each year. This way, we can isolate the proportion of comments across months. Do you notice any patterns?

#### Analysis
 * **[1 pt]** Screamers: It is well known that WRITING IN ALL CAPS ONLINE IS A SUBSTITUTE FOR SCREAMING… OR YELLING. *cough!* Write a job to find users that scream a lot, and provide a screamer score (a highly-technical metric expressed as the percentage of uppercase letters used in their comments).
    * For future reference (when we really want to get something off our chest), what are the top 5 subreddits for scream-y comments?
 * **[3 pt]** Readability: write a job that computes Gunning Fog Index and Flesch-Kincaid Readability (both reading ease and grade level) of user comments. Then:
    * Choose a subreddit and plot the distribution of these scores using a histogram.
    * Find three subreddits of inscrutables, with users that write extremely unreadable comments.
* **[2 pt]** Key Terms: calculate the TF-IDF for a given subreddit.
    * Produce a Tag Cloud of the terms (note: this doesn’t have to be integrated into your code; simply including the image is enough).
 * **[2 pt]** Toxicity: using Sentiment Analysis, determine the top 5 positive subreddits and top 5 negative subreddits based on comment sentiment.
* **[3 pt]** Backstory: given a specific user, find out more about them: where they’re from, what things they like/dislike, and other data about their background (think of at least 2 more things to determine). Note that this should be automated; I should be able to give you a username and you’ll produce a backstory for them. Provide a three sample user backstories in your report (you can clean these up when you add them to the report – they don’t have to be raw comments).
* **[2 pt]** A day in the life: You are a struggling scriptwriter trying to make it big in Hollywood. Find an interesting user with your backstory job, then trace their commenting activities across the site over time. Use this combination of data to build a story about the user’s life: what they do on a regular basis, who their friends are, their hopes/dreams, etc. You have some creative license here.
* **[2 pt]** Matchmaker: while you work on your hit movie script, you need to pay the bills. Use your analysis skills to match up users with similar interests so that they can find love or friendship. If your algorithm is effective, you might just be able to pay rent this month!
    * Note: remember to explain your methodology in your report.
* **[2 pt]** After graduating from USF, you found a startup company that aims to provide personalized music recommendations using big data analysis. In other words, the pitch is that users can “just be themselves” on social media and the service will determine their personality to provide new music recommendations. Design a MapReduce job to do this.
    * Note: remember to explain your methodology in your report.
* **[4 pt]** Now that you’ve found the answers to the questions above, design **two** of your own questions to answer. These should be sufficiently difficult, and you should be creative! You should start with a question, and then propose a predicted answer or hypothesis before writing a MapReduce job to answer it. If you come up with a particularly challenging question, it can count for two (ask first). Some ideas:
    * Visualization of related features. Your visualization should help tell a story.
    * Clustering related users, comments, or subreddits
    * Summary statistics: finding mins, maxes, standard deviations, or even correlations between variables to tell us something about a subreddit or multiple subreddits. For example, perhaps users that visit /r/technology also frequently visit /r/android.
    * Friend graph: can you link together ‘related’ users based on some shared interest? Maybe several users visit the same collection of subreddits. The PageRank could come in handy here.
    
#### Wrap-Up
* **[1 pt]** Project retrospective

#### Hints and Tips
Some hints to remember while you’re analyzing the data:
* The dataset is not censored. We’re all adults here, but don’t put offensive material in your writeup.
* The dataset contains text-based identifiers that allow you to reconstruct threads/replies.
* In some cases, you may want to remove “[deleted]” comments.
* Comments may contain quotes of other users, which you may want to consider as separate from the comment itself. These are prefixed with > (like email replies)
* In some cases, you may be able to get the correct answer without reading every line in the dataset; think about ways you can avoid reading data to speed up your computations.
* While the number of mappers used by the framework is largely a function of the underlying splits in HDFS, you can use job.setNumReduceTasks() to configure the number of reducers. See HowManyMapsAndReduces for a discussion on this.
