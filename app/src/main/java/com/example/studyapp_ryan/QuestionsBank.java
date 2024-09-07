package com.example.studyapp_ryan;


import java.util.ArrayList;
import java.util.List;

public class QuestionsBank {

    private static List<QuestionsList> mobileQuestions(){
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("Which language is primarily used for developing Android applications?","Python","Swift","Kotlin","Ruby","Kotlin","");
        final QuestionsList question2 = new QuestionsList("What is the main purpose of the Android Manifest file in an app?","To display content on the screen","To declare app permissions and components"," To store user data locally","To manage app updates","To declare app permissions and components","");
        final QuestionsList question3 = new QuestionsList("What is an API commonly used for in mobile app development?","To animate user interfaces","To make network requests and communicate with servers","To store data locally on the devic","To display images in the app","To make network requests and communicate with servers","");
        final QuestionsList question4 = new QuestionsList("What is the purpose of a RecyclerView in Android development?","To play audio and video files","To display a scrollable list of items efficientl","To manage network connections","To handle user authentication","To display a scrollable list of items efficiently","");
        final QuestionsList question5 = new QuestionsList("What does \"UI\" stand for in mobile app development?","User Integration","User Interaction","User Information","User Interface","User Interface","");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);

        return questionsLists;
    }
    private static List<QuestionsList> javaQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("Which language is primarily used for developing Android applications?", "Python", "Swift", "Kotlin", "Ruby", "Kotlin", "");
        final QuestionsList question2 = new QuestionsList("What is the main purpose of the Android Manifest file in an app?", "To display content on the screen", "To declare app permissions and components", " To store user data locally", "To manage app updates", "To declare app permissions and components", "");
        final QuestionsList question3 = new QuestionsList("What is an API commonly used for in mobile app development?", "To animate user interfaces", "To make network requests and communicate with servers", "To store data locally on the devic", "To display images in the app", "To make network requests and communicate with servers", "");
        final QuestionsList question4 = new QuestionsList("What is the purpose of a RecyclerView in Android development?", "To play audio and video files", "To display a scrollable list of items efficientl", "To manage network connections", "To handle user authentication", "To display a scrollable list of items efficiently", "");
        final QuestionsList question5 = new QuestionsList("What does \"UI\" stand for in mobile app development?", "User Integration", "User Interaction", "User Information", "User Interface", "User Interface", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);

        return questionsLists;
    }
    private static List<QuestionsList> htmlQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("Which language is primarily used for developing Android applications?", "Python", "Swift", "Kotlin", "Ruby", "Kotlin", "");
        final QuestionsList question2 = new QuestionsList("What is the main purpose of the Android Manifest file in an app?", "To display content on the screen", "To declare app permissions and components", " To store user data locally", "To manage app updates", "To declare app permissions and components", "");
        final QuestionsList question3 = new QuestionsList("What is an API commonly used for in mobile app development?", "To animate user interfaces", "To make network requests and communicate with servers", "To store data locally on the devic", "To display images in the app", "To make network requests and communicate with servers", "");
        final QuestionsList question4 = new QuestionsList("What is the purpose of a RecyclerView in Android development?", "To play audio and video files", "To display a scrollable list of items efficientl", "To manage network connections", "To handle user authentication", "To display a scrollable list of items efficiently", "");
        final QuestionsList question5 = new QuestionsList("What does \"UI\" stand for in mobile app development?", "User Integration", "User Interaction", "User Information", "User Interface", "User Interface", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);

        return questionsLists;
    }
    private static List<QuestionsList> androidQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("Which language is primarily used for developing Android applications?", "Python", "Swift", "Kotlin", "Ruby", "Kotlin", "");
        final QuestionsList question2 = new QuestionsList("What is the main purpose of the Android Manifest file in an app?", "To display content on the screen", "To declare app permissions and components", " To store user data locally", "To manage app updates", "To declare app permissions and components", "");
        final QuestionsList question3 = new QuestionsList("What is an API commonly used for in mobile app development?", "To animate user interfaces", "To make network requests and communicate with servers", "To store data locally on the devic", "To display images in the app", "To make network requests and communicate with servers", "");
        final QuestionsList question4 = new QuestionsList("What is the purpose of a RecyclerView in Android development?", "To play audio and video files", "To display a scrollable list of items efficientl", "To manage network connections", "To handle user authentication", "To display a scrollable list of items efficiently", "");
        final QuestionsList question5 = new QuestionsList("What does \"UI\" stand for in mobile app development?", "User Integration", "User Interaction", "User Information", "User Interface", "User Interface", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);

        return questionsLists;
    }

    public static List<QuestionsList> getQuestions(String selectedTopicName) {
        switch (selectedTopicName){
            case "mobile":
                return mobileQuestions();
            case "java":
                return javaQuestions();
            case "html":
                return htmlQuestions();
            default:
                return androidQuestions();
        }
    }
}
