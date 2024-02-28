package xyz.blushyes.controller;

import static xyz.blushyes.constant.Const.Route.AUTH;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import xyz.blushyes.common.R;
import xyz.blushyes.request.RegisterRequest;
import xyz.blushyes.service.AuthService;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
@Tag(name = "校验")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    @Operation
    public R<String> login(@RequestParam String username, @RequestParam String password) {
        return R.ok(authService.login(username, password));
    }

    @PostMapping("/register")
    @Operation
    public R<Void> register(@RequestBody @Valid RegisterRequest request) {
        return R.ok(() -> authService.register(request));
    }

    @GetMapping("/checkState")
    @Operation
    public R<Boolean> checkState() {
        return R.ok(authService.checkState());
    }
}
