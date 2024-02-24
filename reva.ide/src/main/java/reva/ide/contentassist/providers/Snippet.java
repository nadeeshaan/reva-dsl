package reva.ide.contentassist.providers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Snippet {
    private @Setter String insertText;
    private @Setter String label;
    private @Setter int priority;
}
