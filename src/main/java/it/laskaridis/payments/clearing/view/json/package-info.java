/**
 * Contains classes that marshal/unmarshall domain models to/from
 * JSON client representations. There are two kings of classes in this
 * package:
 * <ul>
 *     <li>View classes, postfixed by <code>View</code>, responsible for
 *       rendering domain models into a json format representation acceptable
 *       by clients.</li>
 *     <li>Form classes, postfixed by <code>Form</code>, responsible for
 *       marshalling client's submitted data (in json format) and converting
 *       them into domain model constructs.</li>
 * </ul>
 */
package it.laskaridis.payments.clearing.view.json;
