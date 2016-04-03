import Ember from 'ember';
import WeekContent from 'amplify-web/utils/week-content';

import CreateAppController from 'amplify-web/controllers/create-app';

const CONTENTS_URL = 'https://learnxiny-mediastore.herokuapp.com/contents';

export default CreateAppController.extend({

  applicationName: '',
  allWeeks: [],

  selectedColor: 'Blue',

  isBlueSelected: Ember.computed.equal('selectedColor', 'Blue'),
  isYellowSelected: Ember.computed.equal('selectedColor', 'Yellow'),
  isRedSelected: Ember.computed.equal('selectedColor', 'Red'),
  isGreenSelected: Ember.computed.equal('selectedColor', 'Green'),

  fetchInitial: Ember.on('init', function() {
    var self = this;

    window.manageApp = this;

    $.ajax(CONTENTS_URL).then(result => {

      self.set('exactResult', result);
      let allWeeks = self.get('allWeeks');

      self.setProperties({
        applicationName: result.applicationName,
        emailId: result.emailId,
      });
      result.weeks.forEach((week, weekIndex) => {
        allWeeks.pushObject(WeekContent.create({
          index: weekIndex + 1,
          weekTitle: week.weekTitle,
          videoId: week.videoId,
          shortDescription: week.shortDescription,
          longDescription: week.longDescription,
          steps: week.steps.map(step => {
            return Ember.Object.create({
              index: parseInt(step.stepNumber),
              imageUrl: step.stepImageUrl,
              title: step.stepTitle,
              description: step.stepDescription
            });
          })
        }));
      });
    });
  }),

  actions: {

    selectColorTheme: function(theme) {
      this.set('selectedColor', theme);
    },
    submitChanges: function() {
      let result = this.get('exactResult');

      let actualResult = {
        applicationName: this.get('applicationName'),
        emailId: this.get('emailId'),
        weeks: this.get('allWeeks').map(week => {
          return {
            weekTitle: week.get('weekTitle'),
            videoId: week.get('videoId'),
            shortDescription: week.get('shortDescription'),
            longDescription: week.get('longDescription'),
            steps: week.get('steps').map((step, index) => {
              return {
                stepNumber: (index + 1).toString(),
                stepImageUrl: step.get('imageUrl'),
                stepTitle: step.get('title'),
                stepDescription: step.get('description')
              };
            })
          };
        })
      };

      var request = {
        type: 'POST',
        url: CONTENTS_URL,
        data: JSON.stringify(actualResult),
        contentType: "application/json",
        datatype: 'json',

        // Allow cross domain request.
        crossDomain: true,
      };

      $.ajax(request).then(result => {
        debugger;
      }, err => {
        debugger;
      });
    }
  }
});
