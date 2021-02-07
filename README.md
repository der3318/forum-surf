
## 🌊 Forum Surf

![ver](https://img.shields.io/badge/version-1.2-blue.svg)
![jre](https://img.shields.io/badge/JRE-8%2B-green.svg)
![sdk](https://img.shields.io/badge/Android-SDK%2016%2B-brightgreen.svg)
![coverage](https://img.shields.io/badge/code%20coverage-28%25-yellow.svg)
![license](https://img.shields.io/badge/license-MIT-blueviolet.svg)

View boards, posts and comments of different discussion forums in one single Android application.

![Demo](/images/Demo.png)


### 📝 Frameworks and Libraries

| Name | Description | Version |
| :-: | :-: | :-: |
| [Volley](https://github.com/google/volley) | HTTP library for Android | 1.1.1 |
| [Jsoup](https://jsoup.org) | HTML elements extraction and manipulating | 1.13.1 |
| [Picasso](https://github.com/square/picasso) | image downloading and caching library | 2.71828 |
| [Tabler Icons](https://github.com/tabler/tabler-icons) | high-quality icons|1.4.0|
| [Icon Finder](https://www.iconfinder.com) | vector and raster icons | - |


### 💡 Customize Boards - Option No.1

| Step | Instruction |
| :-: | :- |
| #1 | clone or download the source files |
| #2 | update [forumsurf.json](https://github.com/der3318/forum-surf/blob/main/app/src/main/res/raw/forumsurf.json) |
| #3 | specify name (any preferred string), type (either PTT or DCARD) and the token (should be part of the url) of the board |
| #4 | rebuild the Android Stuiod project and generate new APK to install |


### 💡 Customize Boards - Option No.2

| Step | Instruction |
| :-: | :- |
| #1 | noted that this method only works on Android 5 (SDK version 16-22)  |
| #2 | download and update [forumsurf.json](https://github.com/der3318/forum-surf/blob/main/app/src/main/res/raw/forumsurf.json) |
| #3 | specify name (any preferred string), type (either PTT or DCARD) and the token (should be part of the url) of the board |
| #4 | put the modified configuration file under Downloads folder of the Android device |
| #5 | make sure file access permission is enabled for the app |

![Demo](/images/Permission.png)


### 🛠 Support Other Dicussion Forums

Currently only boards under PTT and DCARD are supported. In order to extend the functionailty to other discussion forums:

- Define new ForumProcessor.Type in [ForumProcessor.java](https://github.com/der3318/forum-surf/blob/main/app/src/main/java/com/der3318/forumsurf/ForumProcessor.java).
- Create a processor class implementation which implements interface [ForumProcessor](https://github.com/der3318/forum-surf/blob/main/app/src/main/java/com/der3318/forumsurf/ForumProcessor.java).
- The class should be named as `[TYPENAME]Procesor.java`. For example, [PTTProcessor.java](https://github.com/der3318/forum-surf/blob/main/app/src/main/java/com/der3318/forumsurf/PTTProcessor.java) is the corresponding forum processor for boards with type PTT.

The processor should have the following functionality:

| `getUrlForPostList(String boardToken, boolean loadMoreData) -> String` |
| :- |
| This is used when querying a set of posts of a specific board (with toekn boardToken). If and only if the user manually clicks button "load more" at the bottom of the UI, parameter loadMoreData will be true. The call is expected to return the URL to which a HTTP/GET request is sent. |

| `convertResponseToPostList(String response) -> List<ForumPost>` |
| :- |
| Given the raw string response of a HTTP/GET request of the above URL, the method should parse and generate the managed [ForumPost](https://github.com/der3318/forum-surf/blob/b45227a6809b6c939c9130bee1fb3d196bbb432e/app/src/main/java/com/der3318/forumsurf/ForumPost.java#L98) objects. |

| `updateTokenUsedToLoadMoreData(String response) -> void` |
| :- |
| The call will have the identical input as the above method, but it is used to update the token (which should be saved as a private class member) for "loadMoreData" scenario. For exmplae, the last returned post ID or a link to next page. With the help of the toekn, `getUrlForPostList(loadMoreData = true)` can correcly compose the URL. |

| `getUrlForPost(String postToken) -> String` |
| :- |
| This is used when querying the deailed information of a specific post (with token postToken). The call is expected to return the URL to which a HTTP/GET request is sent. |

| `updatePostUsingResponse(ForumPost post, String response) -> void` |
| :- |
| Given the raw string response of a HTTP/GET request of the above URL, the method parse and update the members of the [ForumPost](https://github.com/der3318/forum-surf/blob/b45227a6809b6c939c9130bee1fb3d196bbb432e/app/src/main/java/com/der3318/forumsurf/ForumPost.java#L98) (espicially content). |

| `getUrlForCommentList(String postToken) -> String` |
| :- |
| This is used when querying comments of a specific post (with token postToken). The call is expected to return the URL to which a HTTP/GET request is sent. |

| `convertResponseToCommentList(String response) -> List<String>` |
| :- |
| Given the raw string response of a HTTP/GET request of the above URL, the method should parse and generate string comments. |


