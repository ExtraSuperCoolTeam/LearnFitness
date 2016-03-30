import Ember from 'ember';

export default Ember.Object.extend({
  index: null,
  title: "",
  videoId: "",
  items: [],

  videoUrl: Ember.computed('videoId', function() {
    return "https://www.youtube.com/embed/" + this.get('videoId');
  }),
});
