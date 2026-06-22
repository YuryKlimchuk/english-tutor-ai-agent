/**
 * Teacher bounded context.
 * <p>
 * Manages teacher profiles and authentication.
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"dictionary", "student"}
)
package com.hydroyura.eta.teacher;
