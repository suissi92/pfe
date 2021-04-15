package app.com.cms2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {
  
  @GetMapping("/api/test/machinist")
  @PreAuthorize("hasRole('Machinist') or hasRole('ADMIN')  or hasRole('LigneManager') or hasRole('ProjectManager')")
  public String userAccess() {
    return ">>> User Contents!";
  }
 
  @GetMapping("/api/test/lm")
  @PreAuthorize("hasRole('LigneManager') or hasRole('ProjectManager') or hasRole('ADMIN')")
  public String LigneManagertAccess() {
    return ">>> Ligne Management Board";
  }
  
  @GetMapping("/api/test/pm")
  @PreAuthorize("hasRole('ProjectManager') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Project Management Board";
  }
  
  
  
  @GetMapping("/api/test/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }
}