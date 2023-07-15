package com.project.notesapplication;

import com.google.firebase.Timestamp;

public class Note {
    String Title;
    String Content;
    Timestamp timestamp;

    public Note() {
    }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }


}

