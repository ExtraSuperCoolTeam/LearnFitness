import Ember from 'ember';
import WeekContent from 'amplify-web/utils/week-content';

export default Ember.Controller.extend({

  allWeeks: [],

  actions: {
    addWeek() {
      let allWeeks = this.get('allWeeks');
      let newWeek = WeekContent.create({
        index: allWeeks.get('length') + 1,
        title: "",
        videoId: "0IYlUbxwzOQ",
        items: []
      });

      allWeeks.pushObject(newWeek);
    }
  }

});
