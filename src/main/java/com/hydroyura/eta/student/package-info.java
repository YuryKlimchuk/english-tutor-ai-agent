/**
 * Student bounded context.
 * <p>
 * Manages student profiles, progress tracking, and word acquisition history.
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"dictionary :: dictionary", "dictionary :: word"}
)
package com.hydroyura.eta.student;
