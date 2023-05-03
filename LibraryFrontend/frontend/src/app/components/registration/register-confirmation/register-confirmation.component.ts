import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/core/service/_services/auth.service';
import { StorageService } from 'src/app/core/service/_services/storage.service';

@Component({
  selector: 'app-register-confirmation',
  templateUrl: './register-confirmation.component.html',
  styleUrls: ['./register-confirmation.component.css']
})
export class RegisterConfirmationComponent implements OnInit {
  roles: string[] = [];
  isLoggedIn = false;
  username?: string;
  jwtToken?: string;

  constructor(
    private storageService: StorageService, 
    private router: Router, 
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private toastrService: ToastrService) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      this.router.navigateByUrl("/");
    }

    this.activatedRoute.params.subscribe(params => {
      this.jwtToken = params['code'];
    });
  }

  confirmActivation(): void {
    this.authService.confirmRegister(this.jwtToken!).subscribe({
      next: (result: string) => {
        this.toastrService.success("Registration complete", "", { progressBar: true });
        this.router.navigateByUrl("/login");
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
      }
    });
  }
}
