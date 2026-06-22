/**
 * Student bounded context.
 * <p>
 * Manages student profiles, progress tracking, and word acquisition history.
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = "dictionary"
)
package com.hydroyura.eta.student;
