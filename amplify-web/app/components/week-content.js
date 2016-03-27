import Ember from 'ember';

export default Ember.Component.extend({

  actions: {
    addTextContent() {
      let items = this.get('content.items');

      items.pushObject(Ember.Object.create({
        isText: true,
        text: 'asdf'
      }));
    },
    addImageContent() {
      let items = this.get('content.items');

      items.pushObject(Ember.Object.create({
        isImage: true,
        url: 'img/dumbbells.png'
      }));
    },
    removeItem(item) {
      let items = this.get('content.items');
      items.removeObject(item);
    }
  }
});
