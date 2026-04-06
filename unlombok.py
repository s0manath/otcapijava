import os
import re

for root, dirs, files in os.walk('d:/OTCNEW/otc-spring-boot-api/src/main/java/com/otc/api/model'):
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Remove lombok imports
            content = re.sub(r'import lombok\..*;\n', '', content)
            # Remove lombok annotations
            content = re.sub(r'@Data\n', '', content)
            content = re.sub(r'@NoArgsConstructor\n', '', content)
            content = re.sub(r'@AllArgsConstructor\n', '', content)
            
            # Find class name
            class_match = re.search(r'(public )?class (\w+).*?\{', content)
            if class_match:
                class_name = class_match.group(2)
                
                # find all fields
                fields = re.findall(r'private (.*?) (\w+)( \= .+?)?;', content)
                
                getters_setters = ""
                no_args = f"    public {class_name}() {{}}\n"
                args_params = ", ".join([f"{f[0]} {f[1]}" for f in fields])
                all_args = f"    public {class_name}({args_params}) {{\n"
                for type_, name, _ in fields:
                    all_args += f"        this.{name} = {name};\n"
                    # Getter
                    cap_name = name[0].upper() + name[1:]
                    prefix = "is" if type_ == "boolean" else "get"
                    getters_setters += f"    public {type_} {prefix}{cap_name}() {{ return this.{name}; }}\n"
                    # Setter
                    getters_setters += f"    public void set{cap_name}({type_} {name}) {{ this.{name} = {name}; }}\n"
                
                all_args += "    }\n"
                
                # Insert at the end before closing brace
                if getters_setters:
                    content = content[:content.rfind('}')] + f"\n{no_args}\n{all_args}\n{getters_setters}\n}}\n"
                
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
