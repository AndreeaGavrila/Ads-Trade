<div id="top"></div>

<!-- PROJECT LOGO -->
<div align="center">
  <!-- <a href="https://github.com/CojocaruAlexandraFlavia/IoTEAM-Smart-Wardrobe/">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a> -->

<h3 align="center">ADS TRADE</h3>

  <p align="center">
    An open market place for buying and selling things
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#user-stories">User Stories</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
      <ul>
        <li><a href="#entity-relationship-diagram">Entity Relationship Diagram</a></li>
      </ul>
    <li><a href="#license">License</a></li>
  </ol>
</details>


## About The Project

My application is similar to a marketplace platform that is based on posted purchase and sale ads on various topics.
<br> Users have the option to vote on the selling announcements with like or dislike. </br>
<br> When a user posts a purchase ad, the others will present the sale ads suitable to the requirements, so that the buyer can choose the appropriate ad. </br>
<br> I also implemented a way to reward users with badges for their activity on the platform based on collected points after the number of verified selling ads and their popularity. </br>


<p align="right">(<a href="#top">back to top</a>)</p>


### Built With

* [Java](https://www.java.com/en/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)
* [JUnit](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org)
* [Postman](https://www.postman.com/)
* [Swagger](https://swagger.io)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## User Stories

1. As a user, I would like to be able to create a profile on the announcements trade platform so that I can buy or sell different things;
2. As a user, I would like to post buying ads so that people can add their offers through sale announcements;
3. As a user, I would like to post selling ads for people who are searching for a specific thing posted on a purchase ad;
4. As a user, I would like to have full control over my purchase and sale ads so that I can manage them better;
5. As a user, I would like to vote for sale ads so that I can give my feedback;
6. As a user, I would like to change the status of my sale ad based on the availability;
7. As a user, I would like to close my sale ad so that people can view that my announcement is no longer available;
8. As a user, I would like to be able to close a sale ad if it has a purchase offer that is not completed yet and also if the selling is popular enough so that I can mark the end of the trade;
9. As a user, I would like to mark my purchase ad as completed after I found a suitable sale ad for my requirements;
10. As a user, I would like to view all the sale ads available based on their popularity;
11. As a user, I would like to sort the sale ads by different criteria so that I can analyze them better;
12. As a user, I would like to filter the sale ads by different criteria so that I can view only what interests me;
13. As a user, I would like to receive a badge if my sale ad is top-voted so that my credit could be recognized;
14. As a user, I would like to collect points based on my verified sale ads so that I can be rewarded with badges and people can see my credibility;
15. As a user, I would like to view another user profile so that I can determine his credibility;
16. As a user, I would like to have the purchase and sale ads organized on different topics;
17. As a user, I would like to be notified when someone posts a sale ad to my buying announcement so that I can find the information more quickly;
18. As a user, I would like to be notified when someone posts a purchase ad to a topic so that I can reply to it with my sale offers;
19. As a user, I would like to receive a reminder to accept a sale offer to my purchase ad when it is popular enough so that I can mark it as accepted;
20. As a user, I would like to receive a reminder to close a sale ad after the trade is finished;


<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [ ] Create App
    - [ ] Register
    - [ ] Login
    - [ ] User Management
    - [ ] View all existing users

- [ ] Purchase Ad
    - [ ] Management on the purchase ad
    - [ ] Display the purchase ads based on their popularity
    - [ ] Mark as completed a purchase ad after the buying trade is finished

- [ ] Sale Ad
    - [ ] Management on the sale ad
    - [ ] Display the sale ads based on their popularity
    - [ ] Sort the sale ads by different options
    - [ ] Filter the sale ads by different criteria
    - [ ] View all the available sale ads for a specific purchase announcement
    - [ ] Change the sale ad status
    - [ ] Close a sale announcement
    - [ ] Vote for a sale ad with likes or dislikes

- [ ] Purchase Topics
    - [ ] View a specific purchase topic
    - [ ] Display all the purchase topics

- [ ] Sale Topics
    - [ ] View a specific sale topic
    - [ ] Display all the sale topics

- [ ] Badge
    - [ ] Reward a user with a badge
    - [ ] View a specific user's badge
    - [ ] Display all the existing user's badges 
    - [ ] Display all the badges by a given type

- [ ] Notifications
    - [ ] Mark as seen a notification
    - [ ] Notify after receiving a purchase ad
    - [ ] Notify after receiving a sale ad
    - [ ] Reminder to accept a sale ad
    - [ ] Reminder to close a sale ad
 

<p align="right">(<a href="#top">back to top</a>)</p>



### Entity Relationship Diagram

![ERD](https://github.com/AndreeaGavrila/Ads-Trade/blob/master/ERD.png?raw=true)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>
