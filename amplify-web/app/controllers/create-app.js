import Ember from 'ember';

let WeekContent = Ember.Object.extend({
  index: null,
  title: "",
  videoId: "",
  items: [],

  videoUrl: Ember.computed('videoId', function() {
    return "https://www.youtube.com/embed/" + this.get('videoId');
  })
});

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
