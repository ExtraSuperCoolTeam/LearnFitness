import Ember from 'ember';

export default Ember.Component.extend({

  actions: {
    addStep() {
      let items = this.get('content.items');

      items.pushObject(Ember.Object.create({
        index: items.get('length') + 1,
        title: 'Cool Title',
        description: 'Something something description',
        imgUrl: 'img/dumbbells.png'
      }));
    },
    removeWeek() {
      this.get('removeWeek')(this.get('content'));
    },
    removeItem(item) {
      let items = this.get('content.items');
      items.removeObject(item);
    }
  }
});
