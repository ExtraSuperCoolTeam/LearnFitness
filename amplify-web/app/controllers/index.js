import Ember from 'ember';

export default Ember.Controller.extend({
  employees: [
    Ember.Object.create({
      name: 'Dillon McCoy',
      title: 'Software Engineer',
      imgUrl: 'img/team/1.jpg',
    }),
    Ember.Object.create({
      name: 'Jane Chung',
      title: 'Software Engineer',
      imgUrl: 'img/team/2.jpg',
    }),
    Ember.Object.create({
      name: 'Shrikant Pandhare',
      title: 'Software Engineer',
      imgUrl: 'img/team/3.jpg',
    }),
  ],

  portfolioItems: [
    Ember.Object.create({
      imgUrl: 'img/portfolio/cooking_sample.png',
      title: '4 Course Italian Meal',
      subtitle: 'Cooking Lessons'
    }),
    Ember.Object.create({
      imgUrl: 'img/portfolio/makeup_sample.png',
      title: 'Glam Palette',
      subtitle: 'Makeup Tutorial'
    }),
    Ember.Object.create({
      imgUrl: 'img/portfolio/codepath_sample.png',
      title: 'CodePath',
      subtitle: 'Android Class'
    }),
    Ember.Object.create({
      imgUrl: 'img/portfolio/guitar_sample.png',
      title: 'Guitar Pro',
      subtitle: 'Guitar Lessons'
    }),
  ],
});
