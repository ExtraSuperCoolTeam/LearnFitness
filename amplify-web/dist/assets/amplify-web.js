"use strict";
/* jshint ignore:start */

/* jshint ignore:end */

define('amplify-web/app', ['exports', 'ember', 'ember/resolver', 'ember/load-initializers', 'amplify-web/config/environment'], function (exports, _ember, _emberResolver, _emberLoadInitializers, _amplifyWebConfigEnvironment) {

  var App = undefined;

  _ember['default'].MODEL_FACTORY_INJECTIONS = true;

  App = _ember['default'].Application.extend({
    modulePrefix: _amplifyWebConfigEnvironment['default'].modulePrefix,
    podModulePrefix: _amplifyWebConfigEnvironment['default'].podModulePrefix,
    Resolver: _emberResolver['default']
  });

  (0, _emberLoadInitializers['default'])(App, _amplifyWebConfigEnvironment['default'].modulePrefix);

  exports['default'] = App;
});
define('amplify-web/components/app-version', ['exports', 'ember-cli-app-version/components/app-version', 'amplify-web/config/environment'], function (exports, _emberCliAppVersionComponentsAppVersion, _amplifyWebConfigEnvironment) {

  var name = _amplifyWebConfigEnvironment['default'].APP.name;
  var version = _amplifyWebConfigEnvironment['default'].APP.version;

  exports['default'] = _emberCliAppVersionComponentsAppVersion['default'].extend({
    version: version,
    name: name
  });
});
define('amplify-web/components/json-pretty', ['exports'], function (exports) {
    /*global Ember*/

    var JsonPrettyComponent = Ember.Component.extend({
        attributeBindings: ['obj', 'shouldHighlight'],
        classNames: ['json-pretty'],

        obj: null,
        shouldHighlight: true,

        preformattedText: (function () {
            var obj = this.get('obj');
            var out;
            try {
                out = JSON.stringify(obj, null, 4);
            } catch (exc) {
                out = "Failed to parse input obj:\n" + obj;
            }
            if (out && this.get('shouldHighlight')) {
                out = this.highlightSyntax(out);
            }
            return new Ember.Handlebars.SafeString(out);
        }).property('obj'),

        //Thanks to: http://jsfiddle.net/KJQ9K/
        highlightSyntax: function highlightSyntax(json) {
            json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                var cls = 'number';
                if (/^"/.test(match)) {
                    if (/:$/.test(match)) {
                        cls = 'key';
                    } else {
                        cls = 'string';
                    }
                } else if (/true|false/.test(match)) {
                    cls = 'boolean';
                } else if (/null/.test(match)) {
                    cls = 'null';
                }
                return '<span class="' + cls + '">' + match + '</span>';
            });
        }
    });

    exports['default'] = JsonPrettyComponent;
});
define('amplify-web/components/labeled-radio-button', ['exports', 'ember-radio-button/components/labeled-radio-button'], function (exports, _emberRadioButtonComponentsLabeledRadioButton) {
  exports['default'] = _emberRadioButtonComponentsLabeledRadioButton['default'];
});
define('amplify-web/components/multi-select', ['exports', 'ember', 'ember-cli-multiselect/components/multi-select'], function (exports, _ember, _emberCliMultiselectComponentsMultiSelect) {
  exports['default'] = _emberCliMultiselectComponentsMultiSelect['default'];
});
define('amplify-web/components/radio-button', ['exports', 'ember-radio-button/components/radio-button'], function (exports, _emberRadioButtonComponentsRadioButton) {
  exports['default'] = _emberRadioButtonComponentsRadioButton['default'];
});
define('amplify-web/components/week-content', ['exports', 'ember'], function (exports, _ember) {
  exports['default'] = _ember['default'].Component.extend({

    actions: {
      addTextContent: function addTextContent() {
        var items = this.get('content.items');

        items.pushObject(_ember['default'].Object.create({
          isText: true,
          text: 'asdf'
        }));
      },
      addImageContent: function addImageContent() {
        var items = this.get('content.items');

        items.pushObject(_ember['default'].Object.create({
          isImage: true,
          url: 'img/dumbbells.png'
        }));
      }
    }
  });
});
define('amplify-web/controllers/array', ['exports', 'ember'], function (exports, _ember) {
  exports['default'] = _ember['default'].Controller;
});
define("amplify-web/controllers/create-app", ["exports", "ember"], function (exports, _ember) {

  var WeekContent = _ember["default"].Object.extend({
    index: null,
    title: "",
    videoId: "",
    items: [],

    videoUrl: _ember["default"].computed('videoId', function () {
      return "https://www.youtube.com/embed/" + this.get('videoId');
    })
  });

  exports["default"] = _ember["default"].Controller.extend({

    allWeeks: [],

    actions: {
      addWeek: function addWeek() {
        var allWeeks = this.get('allWeeks');
        var newWeek = WeekContent.create({
          index: allWeeks.get('length') + 1,
          title: "",
          videoId: "0IYlUbxwzOQ",
          items: []
        });

        allWeeks.pushObject(newWeek);
      }
    }

  });
});
define('amplify-web/controllers/object', ['exports', 'ember'], function (exports, _ember) {
  exports['default'] = _ember['default'].Controller;
});
define('amplify-web/helpers/get-name', ['exports', 'ember', 'ember-cli-multiselect/helpers/get-name'], function (exports, _ember, _emberCliMultiselectHelpersGetName) {
  exports['default'] = _emberCliMultiselectHelpersGetName['default'];
});
define('amplify-web/initializers/app-version', ['exports', 'ember-cli-app-version/initializer-factory', 'amplify-web/config/environment'], function (exports, _emberCliAppVersionInitializerFactory, _amplifyWebConfigEnvironment) {
  exports['default'] = {
    name: 'App Version',
    initialize: (0, _emberCliAppVersionInitializerFactory['default'])(_amplifyWebConfigEnvironment['default'].APP.name, _amplifyWebConfigEnvironment['default'].APP.version)
  };
});
define('amplify-web/initializers/export-application-global', ['exports', 'ember', 'amplify-web/config/environment'], function (exports, _ember, _amplifyWebConfigEnvironment) {
  exports.initialize = initialize;

  function initialize() {
    var application = arguments[1] || arguments[0];
    if (_amplifyWebConfigEnvironment['default'].exportApplicationGlobal !== false) {
      var value = _amplifyWebConfigEnvironment['default'].exportApplicationGlobal;
      var globalName;

      if (typeof value === 'string') {
        globalName = value;
      } else {
        globalName = _ember['default'].String.classify(_amplifyWebConfigEnvironment['default'].modulePrefix);
      }

      if (!window[globalName]) {
        window[globalName] = application;

        application.reopen({
          willDestroy: function willDestroy() {
            this._super.apply(this, arguments);
            delete window[globalName];
          }
        });
      }
    }
  }

  exports['default'] = {
    name: 'export-application-global',

    initialize: initialize
  };
});
define('amplify-web/router', ['exports', 'ember', 'amplify-web/config/environment'], function (exports, _ember, _amplifyWebConfigEnvironment) {

  var Router = _ember['default'].Router.extend({
    location: _amplifyWebConfigEnvironment['default'].locationType
  });

  Router.map(function () {
    this.route('create-app');
  });

  exports['default'] = Router;
});
define("amplify-web/templates/application", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    return {
      meta: {
        "topLevel": null,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 2,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/application.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var morphs = new Array(1);
        morphs[0] = dom.createMorphAt(fragment, 0, 0, contextualElement);
        dom.insertBoundary(fragment, 0);
        return morphs;
      },
      statements: [["content", "outlet", ["loc", [null, [1, 0], [1, 10]]]]],
      locals: [],
      templates: []
    };
  })());
});
define("amplify-web/templates/components/json-pretty", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    return {
      meta: {
        "topLevel": null,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 2,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/components/json-pretty.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("pre");
        var el2 = dom.createComment("");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var morphs = new Array(1);
        morphs[0] = dom.createMorphAt(dom.childAt(fragment, [0]), 0, 0);
        return morphs;
      },
      statements: [["content", "preformattedText", ["loc", [null, [1, 5], [1, 25]]]]],
      locals: [],
      templates: []
    };
  })());
});
define("amplify-web/templates/components/labeled-radio-button", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    return {
      meta: {
        "topLevel": false,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 10,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/components/labeled-radio-button.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n\n");
        dom.appendChild(el0, el1);
        var el1 = dom.createComment("");
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var morphs = new Array(2);
        morphs[0] = dom.createMorphAt(fragment, 0, 0, contextualElement);
        morphs[1] = dom.createMorphAt(fragment, 2, 2, contextualElement);
        dom.insertBoundary(fragment, 0);
        return morphs;
      },
      statements: [["inline", "radio-button", [], ["changed", "innerRadioChanged", "disabled", ["subexpr", "@mut", [["get", "disabled", ["loc", [null, [3, 13], [3, 21]]]]], [], []], "groupValue", ["subexpr", "@mut", [["get", "groupValue", ["loc", [null, [4, 15], [4, 25]]]]], [], []], "name", ["subexpr", "@mut", [["get", "name", ["loc", [null, [5, 9], [5, 13]]]]], [], []], "required", ["subexpr", "@mut", [["get", "required", ["loc", [null, [6, 13], [6, 21]]]]], [], []], "value", ["subexpr", "@mut", [["get", "value", ["loc", [null, [7, 10], [7, 15]]]]], [], []]], ["loc", [null, [1, 0], [7, 17]]]], ["content", "yield", ["loc", [null, [9, 0], [9, 9]]]]],
      locals: [],
      templates: []
    };
  })());
});
define("amplify-web/templates/components/multi-select", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    var child0 = (function () {
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 3,
              "column": 4
            },
            "end": {
              "line": 3,
              "column": 40
            }
          },
          "moduleName": "amplify-web/templates/components/multi-select.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("Loading ");
          dom.appendChild(el0, el1);
          var el1 = dom.createComment("");
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("...");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
          var morphs = new Array(1);
          morphs[0] = dom.createMorphAt(fragment, 1, 1, contextualElement);
          return morphs;
        },
        statements: [["content", "name", ["loc", [null, [3, 29], [3, 37]]]]],
        locals: [],
        templates: []
      };
    })();
    var child1 = (function () {
      var child0 = (function () {
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 4,
                "column": 12
              },
              "end": {
                "line": 4,
                "column": 58
              }
            },
            "moduleName": "amplify-web/templates/components/multi-select.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode(" (");
            dom.appendChild(el0, el1);
            var el1 = dom.createComment("");
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode(")");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
            var morphs = new Array(1);
            morphs[0] = dom.createMorphAt(fragment, 1, 1, contextualElement);
            return morphs;
          },
          statements: [["content", "selectedRecordsNum", ["loc", [null, [4, 35], [4, 57]]]]],
          locals: [],
          templates: []
        };
      })();
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 3,
              "column": 40
            },
            "end": {
              "line": 4,
              "column": 65
            }
          },
          "moduleName": "amplify-web/templates/components/multi-select.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("\n    ");
          dom.appendChild(el0, el1);
          var el1 = dom.createComment("");
          dom.appendChild(el0, el1);
          var el1 = dom.createComment("");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
          var morphs = new Array(2);
          morphs[0] = dom.createMorphAt(fragment, 1, 1, contextualElement);
          morphs[1] = dom.createMorphAt(fragment, 2, 2, contextualElement);
          dom.insertBoundary(fragment, null);
          return morphs;
        },
        statements: [["content", "name", ["loc", [null, [4, 4], [4, 12]]]], ["block", "if", [["get", "showRecordNum", ["loc", [null, [4, 18], [4, 31]]]]], [], 0, null, ["loc", [null, [4, 12], [4, 65]]]]],
        locals: [],
        templates: [child0]
      };
    })();
    var child2 = (function () {
      var child0 = (function () {
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 16,
                "column": 6
              },
              "end": {
                "line": 18,
                "column": 6
              }
            },
            "moduleName": "amplify-web/templates/components/multi-select.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode("      ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("p");
            var el2 = dom.createElement("span");
            dom.setAttribute(el2, "class", "label label-warning");
            var el3 = dom.createTextNode("Note:");
            dom.appendChild(el2, el3);
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode(" No records matching your filter. Try * to get some records");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes() {
            return [];
          },
          statements: [],
          locals: [],
          templates: []
        };
      })();
      var child1 = (function () {
        var child0 = (function () {
          return {
            meta: {
              "topLevel": null,
              "revision": "Ember@2.1.0",
              "loc": {
                "source": null,
                "start": {
                  "line": 21,
                  "column": 8
                },
                "end": {
                  "line": 26,
                  "column": 8
                }
              },
              "moduleName": "amplify-web/templates/components/multi-select.hbs"
            },
            isEmpty: false,
            arity: 0,
            cachedFragment: null,
            hasRendered: false,
            buildFragment: function buildFragment(dom) {
              var el0 = dom.createDocumentFragment();
              var el1 = dom.createTextNode("        ");
              dom.appendChild(el0, el1);
              var el1 = dom.createElement("tr");
              var el2 = dom.createTextNode("\n          ");
              dom.appendChild(el1, el2);
              var el2 = dom.createElement("td");
              var el3 = dom.createComment("");
              dom.appendChild(el2, el3);
              dom.appendChild(el1, el2);
              var el2 = dom.createTextNode("\n          ");
              dom.appendChild(el1, el2);
              var el2 = dom.createElement("td");
              var el3 = dom.createElement("span");
              var el4 = dom.createComment("");
              dom.appendChild(el3, el4);
              dom.appendChild(el2, el3);
              dom.appendChild(el1, el2);
              var el2 = dom.createTextNode("\n        ");
              dom.appendChild(el1, el2);
              dom.appendChild(el0, el1);
              var el1 = dom.createTextNode("\n");
              dom.appendChild(el0, el1);
              return el0;
            },
            buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
              var element3 = dom.childAt(fragment, [1]);
              var element4 = dom.childAt(element3, [3, 0]);
              var morphs = new Array(3);
              morphs[0] = dom.createMorphAt(dom.childAt(element3, [1]), 0, 0);
              morphs[1] = dom.createElementMorph(element4);
              morphs[2] = dom.createMorphAt(element4, 0, 0);
              return morphs;
            },
            statements: [["inline", "input", [], ["type", "checkbox", "name", "r.selected", "checked", ["subexpr", "@mut", [["get", "r.selected", ["loc", [null, [23, 64], [23, 74]]]]], [], []]], ["loc", [null, [23, 14], [23, 76]]]], ["element", "action", ["select", ["get", "r", ["loc", [null, [24, 38], [24, 39]]]]], [], ["loc", [null, [24, 20], [24, 41]]]], ["inline", "get-name", [["get", "r", ["loc", [null, [24, 53], [24, 54]]]], ["get", "displayName", ["loc", [null, [24, 55], [24, 66]]]]], [], ["loc", [null, [24, 42], [24, 68]]]]],
            locals: [],
            templates: []
          };
        })();
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 18,
                "column": 6
              },
              "end": {
                "line": 29,
                "column": 6
              }
            },
            "moduleName": "amplify-web/templates/components/multi-select.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode("      ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("table");
            dom.setAttribute(el1, "class", "table table-hovered table-striped");
            var el2 = dom.createTextNode("\n      ");
            dom.appendChild(el1, el2);
            var el2 = dom.createElement("tbody");
            var el3 = dom.createTextNode("\n");
            dom.appendChild(el2, el3);
            var el3 = dom.createComment("");
            dom.appendChild(el2, el3);
            var el3 = dom.createTextNode("      ");
            dom.appendChild(el2, el3);
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n      ");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
            var morphs = new Array(1);
            morphs[0] = dom.createMorphAt(dom.childAt(fragment, [1, 1]), 1, 1);
            return morphs;
          },
          statements: [["block", "each", [["get", "r", ["loc", [null, [21, 16], [21, 17]]]], ["get", "in", ["loc", [null, [21, 18], [21, 20]]]], ["get", "filteredRecords", ["loc", [null, [21, 21], [21, 36]]]]], [], 0, null, ["loc", [null, [21, 8], [26, 17]]]]],
          locals: [],
          templates: [child0]
        };
      })();
      var child2 = (function () {
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 31,
                "column": 6
              },
              "end": {
                "line": 36,
                "column": 6
              }
            },
            "moduleName": "amplify-web/templates/components/multi-select.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode("        ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("div");
            dom.setAttribute(el1, "class", "actionbar");
            var el2 = dom.createTextNode("\n          ");
            dom.appendChild(el1, el2);
            var el2 = dom.createElement("button");
            dom.setAttribute(el2, "type", "button");
            dom.setAttribute(el2, "class", "btn btn-danger");
            var el3 = dom.createTextNode("Cancel");
            dom.appendChild(el2, el3);
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n          ");
            dom.appendChild(el1, el2);
            var el2 = dom.createElement("button");
            dom.setAttribute(el2, "type", "button");
            dom.setAttribute(el2, "class", "btn btn-primary");
            var el3 = dom.createComment("");
            dom.appendChild(el2, el3);
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n        ");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
            var element0 = dom.childAt(fragment, [1]);
            var element1 = dom.childAt(element0, [1]);
            var element2 = dom.childAt(element0, [3]);
            var morphs = new Array(3);
            morphs[0] = dom.createElementMorph(element1);
            morphs[1] = dom.createElementMorph(element2);
            morphs[2] = dom.createMorphAt(element2, 0, 0);
            return morphs;
          },
          statements: [["element", "action", ["cancel"], [], ["loc", [null, [33, 18], [33, 37]]]], ["element", "action", ["submit"], [], ["loc", [null, [34, 18], [34, 37]]]], ["content", "submitText", ["loc", [null, [34, 76], [34, 90]]]]],
          locals: [],
          templates: []
        };
      })();
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 5,
              "column": 2
            },
            "end": {
              "line": 39,
              "column": 2
            }
          },
          "moduleName": "amplify-web/templates/components/multi-select.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("  ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("form");
          dom.setAttribute(el1, "role", "form");
          var el2 = dom.createTextNode("\n    ");
          dom.appendChild(el1, el2);
          var el2 = dom.createElement("div");
          dom.setAttribute(el2, "class", "combo-box form-group");
          var el3 = dom.createTextNode("\n      ");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n      ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("br");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n      ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("div");
          dom.setAttribute(el3, "class", "toolbar");
          var el4 = dom.createTextNode("\n          ");
          dom.appendChild(el3, el4);
          var el4 = dom.createElement("button");
          dom.setAttribute(el4, "type", "button");
          dom.setAttribute(el4, "class", "btn btn-default");
          var el5 = dom.createTextNode("Select All");
          dom.appendChild(el4, el5);
          dom.appendChild(el3, el4);
          var el4 = dom.createTextNode("\n          ");
          dom.appendChild(el3, el4);
          var el4 = dom.createElement("button");
          dom.setAttribute(el4, "type", "button");
          dom.setAttribute(el4, "class", "btn btn-default");
          var el5 = dom.createTextNode("De-select All");
          dom.appendChild(el4, el5);
          dom.appendChild(el3, el4);
          var el4 = dom.createTextNode("\n      ");
          dom.appendChild(el3, el4);
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n      ");
          dom.appendChild(el2, el3);
          var el3 = dom.createElement("br");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n\n");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("\n");
          dom.appendChild(el2, el3);
          var el3 = dom.createComment("");
          dom.appendChild(el2, el3);
          var el3 = dom.createTextNode("    ");
          dom.appendChild(el2, el3);
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n  ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
          var element5 = dom.childAt(fragment, [1, 1]);
          var element6 = dom.childAt(element5, [5]);
          var element7 = dom.childAt(element6, [1]);
          var element8 = dom.childAt(element6, [3]);
          var morphs = new Array(5);
          morphs[0] = dom.createMorphAt(element5, 1, 1);
          morphs[1] = dom.createElementMorph(element7);
          morphs[2] = dom.createElementMorph(element8);
          morphs[3] = dom.createMorphAt(element5, 9, 9);
          morphs[4] = dom.createMorphAt(element5, 11, 11);
          return morphs;
        },
        statements: [["inline", "input", [], ["type", "text", "class", "form-control", "value", ["subexpr", "@mut", [["get", "searchText", ["loc", [null, [8, 53], [8, 63]]]]], [], []], "placeholder", "Type to search", "autofocus", "autofocus"], ["loc", [null, [8, 6], [8, 116]]]], ["element", "action", ["selectAll"], [], ["loc", [null, [11, 18], [11, 40]]]], ["element", "action", ["deselectAll"], [], ["loc", [null, [12, 18], [12, 42]]]], ["block", "if", [["get", "noRecords", ["loc", [null, [16, 12], [16, 21]]]]], [], 0, 1, ["loc", [null, [16, 6], [29, 13]]]], ["block", "if", [["get", "submit", ["loc", [null, [31, 12], [31, 18]]]]], [], 2, null, ["loc", [null, [31, 6], [36, 13]]]]],
        locals: [],
        templates: [child0, child1, child2]
      };
    })();
    return {
      meta: {
        "topLevel": null,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 41,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/components/multi-select.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("div");
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("button");
        dom.setAttribute(el2, "class", "btn btn-default dropdown-toggle");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createComment("");
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode(" ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("span");
        dom.setAttribute(el3, "class", "caret");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment("");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var element9 = dom.childAt(fragment, [0]);
        var element10 = dom.childAt(element9, [1]);
        var morphs = new Array(5);
        morphs[0] = dom.createElementMorph(element9);
        morphs[1] = dom.createElementMorph(element10);
        morphs[2] = dom.createElementMorph(element10);
        morphs[3] = dom.createMorphAt(element10, 1, 1);
        morphs[4] = dom.createMorphAt(element9, 3, 3);
        return morphs;
      },
      statements: [["element", "bind-attr", [], ["class", ":multi-select isOpen:open"], ["loc", [null, [1, 5], [1, 52]]]], ["element", "action", ["toggleOpen"], [], ["loc", [null, [2, 50], [2, 73]]]], ["element", "bind-attr", [], ["disabled", "isLoading"], ["loc", [null, [2, 74], [2, 108]]]], ["block", "if", [["get", "isLoading", ["loc", [null, [3, 10], [3, 19]]]]], [], 0, 1, ["loc", [null, [3, 4], [4, 72]]]], ["block", "if", [["get", "isOpen", ["loc", [null, [5, 8], [5, 14]]]]], [], 2, null, ["loc", [null, [5, 2], [39, 9]]]]],
      locals: [],
      templates: [child0, child1, child2]
    };
  })());
});
define("amplify-web/templates/components/week-content", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    var child0 = (function () {
      var child0 = (function () {
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 33,
                "column": 12
              },
              "end": {
                "line": 38,
                "column": 12
              }
            },
            "moduleName": "amplify-web/templates/components/week-content.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode("              ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("label");
            dom.setAttribute(el1, "class", "col-lg-2");
            var el2 = dom.createTextNode("Text content:");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n              ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("div");
            dom.setAttribute(el1, "class", "col-lg-10 text-content-area");
            var el2 = dom.createTextNode("\n                ");
            dom.appendChild(el1, el2);
            var el2 = dom.createComment("");
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n              ");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
            var morphs = new Array(1);
            morphs[0] = dom.createMorphAt(dom.childAt(fragment, [3]), 1, 1);
            return morphs;
          },
          statements: [["inline", "input", [], ["type", "textarea", "value", ["subexpr", "@mut", [["get", "item.text", ["loc", [null, [36, 46], [36, 55]]]]], [], []]], ["loc", [null, [36, 16], [36, 57]]]]],
          locals: [],
          templates: []
        };
      })();
      var child1 = (function () {
        return {
          meta: {
            "topLevel": null,
            "revision": "Ember@2.1.0",
            "loc": {
              "source": null,
              "start": {
                "line": 40,
                "column": 12
              },
              "end": {
                "line": 48,
                "column": 12
              }
            },
            "moduleName": "amplify-web/templates/components/week-content.hbs"
          },
          isEmpty: false,
          arity: 0,
          cachedFragment: null,
          hasRendered: false,
          buildFragment: function buildFragment(dom) {
            var el0 = dom.createDocumentFragment();
            var el1 = dom.createTextNode("              ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("label");
            dom.setAttribute(el1, "class", "col-lg-2");
            var el2 = dom.createTextNode("Image content:");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n\n              ");
            dom.appendChild(el0, el1);
            var el1 = dom.createElement("div");
            dom.setAttribute(el1, "class", "col-lg-10 image-content-area");
            var el2 = dom.createTextNode("\n                ");
            dom.appendChild(el1, el2);
            var el2 = dom.createComment("");
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n\n                ");
            dom.appendChild(el1, el2);
            var el2 = dom.createElement("img");
            dom.setAttribute(el2, "class", "img-preview");
            dom.appendChild(el1, el2);
            var el2 = dom.createTextNode("\n              ");
            dom.appendChild(el1, el2);
            dom.appendChild(el0, el1);
            var el1 = dom.createTextNode("\n");
            dom.appendChild(el0, el1);
            return el0;
          },
          buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
            var element0 = dom.childAt(fragment, [3]);
            var element1 = dom.childAt(element0, [3]);
            var morphs = new Array(2);
            morphs[0] = dom.createMorphAt(element0, 1, 1);
            morphs[1] = dom.createAttrMorph(element1, 'src');
            return morphs;
          },
          statements: [["inline", "input", [], ["type", "text", "value", ["subexpr", "@mut", [["get", "item.url", ["loc", [null, [44, 42], [44, 50]]]]], [], []], "placeholder", "https://imgur.com/asdf"], ["loc", [null, [44, 16], [44, 89]]]], ["attribute", "src", ["get", "item.url", ["loc", [null, [46, 47], [46, 55]]]]]],
          locals: [],
          templates: []
        };
      })();
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 31,
              "column": 8
            },
            "end": {
              "line": 50,
              "column": 8
            }
          },
          "moduleName": "amplify-web/templates/components/week-content.hbs"
        },
        isEmpty: false,
        arity: 1,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("          ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("div");
          dom.setAttribute(el1, "class", "row");
          var el2 = dom.createTextNode("\n");
          dom.appendChild(el1, el2);
          var el2 = dom.createComment("");
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("\n");
          dom.appendChild(el1, el2);
          var el2 = dom.createComment("");
          dom.appendChild(el1, el2);
          var el2 = dom.createTextNode("          ");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
          var element2 = dom.childAt(fragment, [1]);
          var morphs = new Array(2);
          morphs[0] = dom.createMorphAt(element2, 1, 1);
          morphs[1] = dom.createMorphAt(element2, 3, 3);
          return morphs;
        },
        statements: [["block", "if", [["get", "item.isText", ["loc", [null, [33, 18], [33, 29]]]]], [], 0, null, ["loc", [null, [33, 12], [38, 19]]]], ["block", "if", [["get", "item.isImage", ["loc", [null, [40, 18], [40, 30]]]]], [], 1, null, ["loc", [null, [40, 12], [48, 19]]]]],
        locals: ["item"],
        templates: [child0, child1]
      };
    })();
    var child1 = (function () {
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 50,
              "column": 8
            },
            "end": {
              "line": 52,
              "column": 8
            }
          },
          "moduleName": "amplify-web/templates/components/week-content.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("          Add photos and text content to explain the video.\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes() {
          return [];
        },
        statements: [],
        locals: [],
        templates: []
      };
    })();
    return {
      meta: {
        "topLevel": null,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 63,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/components/week-content.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("div");
        dom.setAttribute(el1, "class", "panel panel-default");
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("div");
        dom.setAttribute(el2, "class", "panel-heading");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h3");
        dom.setAttribute(el3, "class", "panel-title");
        var el4 = dom.createTextNode("Week ");
        dom.appendChild(el3, el4);
        var el4 = dom.createComment("");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n  ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("div");
        dom.setAttribute(el2, "class", "panel-body");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "form-group");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("label");
        dom.setAttribute(el4, "for", "week1Title");
        dom.setAttribute(el4, "class", "col-lg-2");
        var el5 = dom.createTextNode("Title:");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "col-lg-10");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "form-group");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("label");
        dom.setAttribute(el4, "for", "week1Video");
        dom.setAttribute(el4, "class", "col-lg-2");
        var el5 = dom.createTextNode("YouTube Video ID:");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "col-lg-10");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "row");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-12");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createComment("");
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "row");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-12");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("iframe");
        dom.setAttribute(el7, "width", "560");
        dom.setAttribute(el7, "height", "315");
        dom.setAttribute(el7, "frameborder", "0");
        dom.setAttribute(el7, "allowfullscreen", "");
        var el8 = dom.createTextNode("\n            ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "form-group");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("label");
        dom.setAttribute(el4, "for", "exampleInputFile");
        dom.setAttribute(el4, "class", "col-lg-2");
        var el5 = dom.createTextNode("Lesson Content");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "col-lg-10");
        var el5 = dom.createTextNode("\n");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "form-group");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "col-lg-10 col-lg-offset-2");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        dom.setAttribute(el5, "type", "button");
        var el6 = dom.createTextNode("Add an Image");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("button");
        dom.setAttribute(el5, "type", "button");
        var el6 = dom.createTextNode("Add Text Content");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n  ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var element3 = dom.childAt(fragment, [0]);
        var element4 = dom.childAt(element3, [3]);
        var element5 = dom.childAt(element4, [3, 3]);
        var element6 = dom.childAt(element5, [3, 1, 1]);
        var element7 = dom.childAt(element4, [7, 1]);
        var element8 = dom.childAt(element7, [1]);
        var element9 = dom.childAt(element7, [3]);
        var morphs = new Array(7);
        morphs[0] = dom.createMorphAt(dom.childAt(element3, [1, 1]), 1, 1);
        morphs[1] = dom.createMorphAt(dom.childAt(element4, [1, 3]), 1, 1);
        morphs[2] = dom.createMorphAt(dom.childAt(element5, [1, 1]), 1, 1);
        morphs[3] = dom.createAttrMorph(element6, 'src');
        morphs[4] = dom.createMorphAt(dom.childAt(element4, [5, 3]), 1, 1);
        morphs[5] = dom.createElementMorph(element8);
        morphs[6] = dom.createElementMorph(element9);
        return morphs;
      },
      statements: [["content", "content.index", ["loc", [null, [3, 33], [3, 50]]]], ["inline", "input", [], ["type", "text", "class", "form-control", "placeholder", "Abs and Core", "value", ["subexpr", "@mut", [["get", "content.title", ["loc", [null, [9, 82], [9, 95]]]]], [], []]], ["loc", [null, [9, 8], [9, 97]]]], ["inline", "input", [], ["type", "text", "placeholder", "Place your YouTube video id here", "value", ["subexpr", "@mut", [["get", "content.videoId", ["loc", [null, [17, 85], [17, 100]]]]], [], []]], ["loc", [null, [17, 12], [17, 102]]]], ["attribute", "src", ["get", "content.videoUrl", ["loc", [null, [22, 51], [22, 67]]]]], ["block", "each", [["get", "content.items", ["loc", [null, [31, 16], [31, 29]]]]], [], 0, 1, ["loc", [null, [31, 8], [52, 17]]]], ["element", "action", ["addImageContent"], [], ["loc", [null, [57, 30], [57, 58]]]], ["element", "action", ["addTextContent"], [], ["loc", [null, [58, 30], [58, 57]]]]],
      locals: [],
      templates: [child0, child1]
    };
  })());
});
define("amplify-web/templates/create-app", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    var child0 = (function () {
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 28,
              "column": 8
            },
            "end": {
              "line": 30,
              "column": 8
            }
          },
          "moduleName": "amplify-web/templates/create-app.hbs"
        },
        isEmpty: false,
        arity: 1,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("          ");
          dom.appendChild(el0, el1);
          var el1 = dom.createComment("");
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
          var morphs = new Array(1);
          morphs[0] = dom.createMorphAt(fragment, 1, 1, contextualElement);
          return morphs;
        },
        statements: [["inline", "week-content", [], ["content", ["subexpr", "@mut", [["get", "week", ["loc", [null, [29, 33], [29, 37]]]]], [], []]], ["loc", [null, [29, 10], [29, 39]]]]],
        locals: ["week"],
        templates: []
      };
    })();
    var child1 = (function () {
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 30,
              "column": 8
            },
            "end": {
              "line": 32,
              "column": 8
            }
          },
          "moduleName": "amplify-web/templates/create-app.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("          ");
          dom.appendChild(el0, el1);
          var el1 = dom.createElement("div");
          dom.setAttribute(el1, "class", "alert alert-info");
          var el2 = dom.createTextNode("No content added yet.");
          dom.appendChild(el1, el2);
          dom.appendChild(el0, el1);
          var el1 = dom.createTextNode("\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes() {
          return [];
        },
        statements: [],
        locals: [],
        templates: []
      };
    })();
    return {
      meta: {
        "topLevel": null,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 50,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/create-app.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("div");
        dom.setAttribute(el1, "class", "container");
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("div");
        dom.setAttribute(el2, "class", "page-header");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("h1");
        var el4 = dom.createTextNode("Learn Fitness ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("small");
        var el5 = dom.createTextNode("Create Your Custom Android App");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n  ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("div");
        dom.setAttribute(el2, "class", "row");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "col-lg-12");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("form");
        dom.setAttribute(el4, "class", "form-horizontal");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "form-group");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("label");
        dom.setAttribute(el6, "for", "appName");
        dom.setAttribute(el6, "class", "col-lg-2");
        var el7 = dom.createTextNode("Application Name");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-10");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("input");
        dom.setAttribute(el7, "type", "text");
        dom.setAttribute(el7, "class", "form-control");
        dom.setAttribute(el7, "id", "appName");
        dom.setAttribute(el7, "placeholder", "My Cool App");
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "form-group");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("label");
        dom.setAttribute(el6, "for", "email");
        dom.setAttribute(el6, "class", "col-lg-2");
        var el7 = dom.createTextNode("Contact Email");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-10");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("input");
        dom.setAttribute(el7, "type", "email");
        dom.setAttribute(el7, "class", "form-control");
        dom.setAttribute(el7, "id", "email");
        dom.setAttribute(el7, "placeholder", "contact@email.com");
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "form-group");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("label");
        dom.setAttribute(el6, "for", "exampleInputPassword1");
        dom.setAttribute(el6, "class", "col-lg-2");
        var el7 = dom.createTextNode("Password");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-10");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("input");
        dom.setAttribute(el7, "type", "password");
        dom.setAttribute(el7, "class", "form-control");
        dom.setAttribute(el7, "id", "exampleInputPassword1");
        dom.setAttribute(el7, "placeholder", "Password");
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("hr");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("h3");
        var el6 = dom.createTextNode("Lesson Content");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n");
        dom.appendChild(el4, el5);
        var el5 = dom.createComment("");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "form-group");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-12");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("button");
        dom.setAttribute(el7, "class", "btn btn-default");
        var el8 = dom.createElement("span");
        dom.setAttribute(el8, "class", "glyphicon\nglyphicon-plus");
        dom.appendChild(el7, el8);
        var el8 = dom.createTextNode("\n            Add a Week\n            ");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "form-group");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("div");
        dom.setAttribute(el6, "class", "col-lg-12 submit-btn-div");
        var el7 = dom.createTextNode("\n            ");
        dom.appendChild(el6, el7);
        var el7 = dom.createElement("button");
        dom.setAttribute(el7, "type", "submit");
        dom.setAttribute(el7, "class", "submit-btn btn btn-danger");
        var el8 = dom.createTextNode("Create My App");
        dom.appendChild(el7, el8);
        dom.appendChild(el6, el7);
        var el7 = dom.createTextNode("\n          ");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n  ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var element0 = dom.childAt(fragment, [0, 3, 1, 1]);
        var element1 = dom.childAt(element0, [13, 1, 1]);
        var morphs = new Array(2);
        morphs[0] = dom.createMorphAt(element0, 11, 11);
        morphs[1] = dom.createElementMorph(element1);
        return morphs;
      },
      statements: [["block", "each", [["get", "allWeeks", ["loc", [null, [28, 16], [28, 24]]]]], [], 0, 1, ["loc", [null, [28, 8], [32, 17]]]], ["element", "action", ["addWeek"], [], ["loc", [null, [35, 44], [35, 64]]]]],
      locals: [],
      templates: [child0, child1]
    };
  })());
});
define("amplify-web/templates/index", ["exports"], function (exports) {
  exports["default"] = Ember.HTMLBars.template((function () {
    var child0 = (function () {
      return {
        meta: {
          "topLevel": null,
          "revision": "Ember@2.1.0",
          "loc": {
            "source": null,
            "start": {
              "line": 5,
              "column": 2
            },
            "end": {
              "line": 7,
              "column": 2
            }
          },
          "moduleName": "amplify-web/templates/index.hbs"
        },
        isEmpty: false,
        arity: 0,
        cachedFragment: null,
        hasRendered: false,
        buildFragment: function buildFragment(dom) {
          var el0 = dom.createDocumentFragment();
          var el1 = dom.createTextNode("    Begin today\n");
          dom.appendChild(el0, el1);
          return el0;
        },
        buildRenderNodes: function buildRenderNodes() {
          return [];
        },
        statements: [],
        locals: [],
        templates: []
      };
    })();
    return {
      meta: {
        "topLevel": false,
        "revision": "Ember@2.1.0",
        "loc": {
          "source": null,
          "start": {
            "line": 1,
            "column": 0
          },
          "end": {
            "line": 41,
            "column": 0
          }
        },
        "moduleName": "amplify-web/templates/index.hbs"
      },
      isEmpty: false,
      arity: 0,
      cachedFragment: null,
      hasRendered: false,
      buildFragment: function buildFragment(dom) {
        var el0 = dom.createDocumentFragment();
        var el1 = dom.createElement("div");
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("img");
        dom.setAttribute(el2, "class", "main-body-img");
        dom.setAttribute(el2, "src", "img/background.jpg");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("img");
        dom.setAttribute(el2, "class", "logo");
        dom.setAttribute(el2, "src", "img/amplify-logo.png");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("p");
        dom.setAttribute(el2, "class", "tagline");
        var el3 = dom.createTextNode("Create your own app in minutes");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment("");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        var el1 = dom.createElement("div");
        dom.setAttribute(el1, "class", "main-content container");
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createComment(" Row for images and text ");
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n  ");
        dom.appendChild(el1, el2);
        var el2 = dom.createElement("div");
        dom.setAttribute(el2, "class", "row");
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "col-lg-4");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "thumbnail");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("img");
        dom.setAttribute(el5, "src", "img/exercise-balls.jpg");
        dom.setAttribute(el5, "alt", "...");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "caption");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("h3");
        var el7 = dom.createTextNode("Create Custom Lessons");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("p");
        var el7 = dom.createTextNode("Create speciality training videos along with image and textual instructions");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "col-lg-4");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "thumbnail");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("img");
        dom.setAttribute(el5, "src", "img/pushup-woman.jpg");
        dom.setAttribute(el5, "alt", "...");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "caption");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("h3");
        var el7 = dom.createTextNode("Provide User Feedback");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("p");
        var el7 = dom.createTextNode("Connect users with local trainer, review user's posted video messages and provide feedback anywhere from the world");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n    ");
        dom.appendChild(el2, el3);
        var el3 = dom.createElement("div");
        dom.setAttribute(el3, "class", "col-lg-4");
        var el4 = dom.createTextNode("\n      ");
        dom.appendChild(el3, el4);
        var el4 = dom.createElement("div");
        dom.setAttribute(el4, "class", "thumbnail");
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("img");
        dom.setAttribute(el5, "src", "img/touch-toes.jpg");
        dom.setAttribute(el5, "alt", "...");
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n        ");
        dom.appendChild(el4, el5);
        var el5 = dom.createElement("div");
        dom.setAttribute(el5, "class", "caption");
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("h3");
        var el7 = dom.createTextNode("Generate Trainer Referrals");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n          ");
        dom.appendChild(el5, el6);
        var el6 = dom.createElement("p");
        var el7 = dom.createTextNode("Make trainer profiles available to your users");
        dom.appendChild(el6, el7);
        dom.appendChild(el5, el6);
        var el6 = dom.createTextNode("\n        ");
        dom.appendChild(el5, el6);
        dom.appendChild(el4, el5);
        var el5 = dom.createTextNode("\n      ");
        dom.appendChild(el4, el5);
        dom.appendChild(el3, el4);
        var el4 = dom.createTextNode("\n    ");
        dom.appendChild(el3, el4);
        dom.appendChild(el2, el3);
        var el3 = dom.createTextNode("\n  ");
        dom.appendChild(el2, el3);
        dom.appendChild(el1, el2);
        var el2 = dom.createTextNode("\n");
        dom.appendChild(el1, el2);
        dom.appendChild(el0, el1);
        var el1 = dom.createTextNode("\n");
        dom.appendChild(el0, el1);
        return el0;
      },
      buildRenderNodes: function buildRenderNodes(dom, fragment, contextualElement) {
        var morphs = new Array(1);
        morphs[0] = dom.createMorphAt(dom.childAt(fragment, [0]), 7, 7);
        return morphs;
      },
      statements: [["block", "link-to", ["create-app"], ["class", "create-button btn btn-primary btn-lg"], 0, null, ["loc", [null, [5, 2], [7, 14]]]]],
      locals: [],
      templates: [child0]
    };
  })());
});
/* jshint ignore:start */

/* jshint ignore:end */

/* jshint ignore:start */

define('amplify-web/config/environment', ['ember'], function(Ember) {
  var prefix = 'amplify-web';
/* jshint ignore:start */

try {
  var metaName = prefix + '/config/environment';
  var rawConfig = Ember['default'].$('meta[name="' + metaName + '"]').attr('content');
  var config = JSON.parse(unescape(rawConfig));

  return { 'default': config };
}
catch(err) {
  throw new Error('Could not read config from meta tag with name "' + metaName + '".');
}

/* jshint ignore:end */

});

if (!runningTests) {
  require("amplify-web/app")["default"].create({"name":"amplify-web","version":"0.0.0+2f32dde4"});
}

/* jshint ignore:end */
//# sourceMappingURL=amplify-web.map