import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import './components/my-comp.js';

/**
 * @customElement
 * @polymer
 */
class P3testApp extends PolymerElement {
  static get template() {
    return html`
      <style>
        :host {
          display: block;
        }
      </style>
      <h2>Hello [[prop1]]!</h2>
      <my-comp></my-comp>
    `;
  }
  static get properties() {
    return {
      prop1: {
        type: String,
        value: 'p3test-app'
      }
    };
  }
}

window.customElements.define('p3test-app', P3testApp);
