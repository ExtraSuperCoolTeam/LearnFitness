import Ember from 'ember';
import WeekContent from 'amplify-web/utils/week-content';

import CreateAppController from 'amplify-web/controllers/create-app';

export default CreateAppController.extend({

  allWeeks: [],

  selectedColor: 'Blue',

  isBlueSelected: Ember.computed.equal('selectedColor', 'Blue'),
  isNeutralSelected: Ember.computed.equal('selectedColor', 'Neutral'),
  isRedSelected: Ember.computed.equal('selectedColor', 'Red'),

  fetchInitial: Ember.on('init', function() {
    var self = this;

    $.ajax('https://learnxiny-mediastore.herokuapp.com/contents').then(result => {
      let allWeeks = self.get('allWeeks');

      result.weeks.forEach(week => {
        allWeeks.pushObject(WeekContent.create({
          weekTitle: week.weekTitle,
          videoId: week.videoId,
          shortDescription: week.shortDescription,
          longDescription: week.longDescription
        }));
      });
    });
  }),

  actions: {

    selectColorTheme: function(theme) {
      this.set('selectedColor', theme);
    }
  }
});
