package org.example.api.joinrequest;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -3901686945998609592L;

    /**
     *
     */
    @TableId
    private Long id;
}
