/**
 * Teacher bounded context.
 * <p>
 * Manages teacher profiles and authentication.
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"dictionary :: dictionary", "student :: student", "student :: lesson"}
)
package com.hydroyura.eta.teacher;
